/* eslint-disable camelcase */
/* eslint-disable consistent-return */
/* eslint-disable no-console */
const bcrypt = require('bcrypt');
const passport = require('passport');
const LocalStrategy = require('passport-local').Strategy;
const JWTstrategy = require('passport-jwt').Strategy;
const ExtractJWT = require('passport-jwt').ExtractJwt;
const User = require('./models/MUser');
const jwtSecret = require('./jwtConfig');
const jwt = require('jsonwebtoken');
const sms = require('./ext/sms');
const OTP = require('./ext/otp');
const odoo = require('./odoo_server');

const BCRYPT_SALT_ROUNDS = 12;
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;

function auth_pass({ server }) {
  passport.use(
    'register',
    new LocalStrategy(
      {
        usernameField: 'mobile',
        passwordField: 'pin',
        passReqToCallback: true,
        session: false,
      },
      (req,mobile, pin,done) => {
        console.log(req.body.username);
        console.log(mobile);
        console.log(pin);
        console.log(done);
        try {
          User.findOne({
            mobile: mobile
          }).then((user) => {
            if (user === null) {
              bcrypt.hash(pin, BCRYPT_SALT_ROUNDS).then((hashedPin) => {
                User.add({
                  mobile,
                  pin: hashedPin,
                  name:req.body.username,
                }).then((user) => {
                  console.log('user created');
                  return done(null, user);
                });
              });
            } else {
              return done(null, false, { message: 'Duplicate Mobile - Cannot register' });
            }
          });
        } catch (err) {
          return done(err);
        }
      },
    ),
  );
  passport.use(
    'login',
    new LocalStrategy(
      {
        usernameField: 'mobile',
        passwordField: 'pin',
        session: false,
      },
      (mobile, pin, done) => {
        try {
          console.log("Hello from passport login");
          console.log("mobile and pin : " + mobile + pin);
          User.findOne({
            mobile: mobile
          }).then((user) => {
            if (user === null) {
              return done(null, false, { message: 'bad username' });
            }
            bcrypt.compare(pin, user.pin).then((response) => {
              if (response !== true) {
                console.log('passwords do not match');
                return done(null, false, { message: 'passwords do not match' });
              }
              console.log("Trying to create Odoo Session");
              oserver = odoo.getOdoo(user.username,user.password);
              if( oserver === undefined){
                console.log('Odoo server connection issue');
                return done(null, false, { message: 'cannot connect to DMS' });
              }
              console.log('user found & authenticated');
              return done(null, user);
            });
          });
        } catch (err) {
          done(err);
        }
      },
    ),
  );

  const opts = {
    jwtFromRequest: ExtractJWT.fromAuthHeaderWithScheme('JWT'),
    secretOrKey: jwtSecret.secret,
  };

  passport.use(
    'jwt',
    new JWTstrategy(opts, async (jwt_payload, done) => {
      try {
        console.log("the user id is " + jwt_payload.id);
        const user = await User.findById(jwt_payload.id);
        if (user) {
          console.log('user found in db in passport');
          done(null, user);
        } else {
          console.log('user not found in db');
          done(null, false);
        }
      } catch (err) {
        done(err);
      }
    }),
  );
  server.use(passport.initialize());
  server.post('/login', (req, res, next) => {
    console.log("Doing LOGIN");
    passport.authenticate('login', (err, user, info) => {
      console.log("HALOOOOO");
      if (err) {
        console.error(`error ${err}`);
      }
      if (info !== undefined) {
        console.error(info.message);
        if (info.message === 'bad username') {
          res.status(401).send({'error':info.message});
        } else {
          res.status(403).send({'error':info.message});
        }
      } else {
        const token = jwt.sign({ id: user.id }, jwtSecret.secret);
        res.status(200).send({
          auth: true,
          token,
          message: 'user found & logged in',
        });
        console.log("Successful Login");
      }
    })(req, res, next);
  });
  server.post('/register', (req, res, next) => {
    console.log("Doing Registration");
    passport.authenticate('register', (err, user, info) => {
      console.log("HALOOOOO Reg");
      if (err) {
        console.error(`error ${err}`);
      }
      if (info !== undefined) {
        console.error(info.message);
        if (info.message === 'bad username') {
          res.status(401).send(info.message);
        } else {
          res.status(403).send(info.message);
        }
      } else {
        res.status(200).send({
          auth: true,
          message: 'user succesfully registered',
        });
        console.log("Successful Login");
      }
    })(req, res, next);
  });
  server.post('/otp_verify', async (req, res, next) => {
    valid = OTP.validate({ token: req.body.token })
    if (!valid) {
      res.status(401).send({
        message: "Bad OTP try again"
      });
    } else {
      res.status(200).send({
        message: 'OTP Verified',
      });
      console.log("Successful OTP verification");
    }

  });
  server.post('/verify', async (req, res, next) => {
    console.log("Doing Verification");
    const user = await User.findOne({ mobile: req.body.mobile }).lean();
    if (user === null) {
      res.status(401).send({
        message: "Bad Mobile provided"
      });
      otp_val = await OTP.verify();
      console.log(otp_val);
      otp_message = " Thanks for downloading Book a service app please use OTP " + otp_val.token;
      log_message = "Sent Message to " + req.body.mobile
      sms('9840021822', log_message.replace(/ /g, "%20"));
      sms(req.body.mobile, otp_message.replace(/ /g, "%20"));
    } else {
      res.status(200).send({
        message: 'mobile present in DB',
      });
      console.log("Successful Login");
    }
  });
};
module.exports = auth_pass;
