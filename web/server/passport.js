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
const firebase = require('./ext/firebase');

const BCRYPT_SALT_ROUNDS = 12;
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;

function auth_pass({ server }) {

  passport.use(
    'login',
    new LocalStrategy(
      {
        usernameField: 'mobile',
        passwordField: 'password',
        session: false,
      },
      (mobile, password, done) => {
        try {
          //console.log(req.body);
          console.log("Hello from passport login");
          console.log("mobile and password : " + mobile + password);
          User.findOne({
            mobile: mobile
          }).then((user) => {
            if (user === null) {
              return done(null, false, { message: 'bad username' });
            }
            console.log("Trying to create Odoo Session");
            oserver = odoo.getOdoo(user.email, password);
            console.log(oserver);
            if (oserver.sid) {
              console.log("trying to logout");
              oserver.logout();
            }
            oserver.connect(async function (err, result) {
              if (err) {
                return done(null, false, { message: 'cannot connect to DMS' });
              }
              console.log('user found & authenticated');
              model = 'res.partner';
              let im_result = await oserver.search_read(model, { domain: [["id", "=", user.partner_id]], fields: ["id", "image"] });
              user.image = im_result.records[0].image;
              return done(null, user);
            });
          });
        } catch (err) {
          console.log("Error o: ", err);
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
  server.get('/user/avatar/:id', async (req, res) => {
    console.log(req);
    try {
      let server = odoo.getOdoo(req.user.name);
      id = parseInt(req.params.id);
      model = 'res.partner';
      let result = await server.search_read(model, { domain: [["id", "=", id]], fields: ["id", "image"] });
      console.log(model + '', result);
      console.log(model + '...', result[0]);
      res.json(result);
    } catch (err) {
      res.json({ error: err.message || err.toString() });
    }
  });
  server.post('/login', (req, res, next) => {
    console.log("Doing LOGIN");
    console.log("The request ...",req);
    passport.authenticate('login', (err, user, info) => {
      console.log("HALOOOOO");
      if (err) {
        console.error(`error ${err}`);
      }
      if (info !== undefined) {
        console.error(info.message);
        if (info.message === 'bad username') {
          res.status(401).send({ 'error': info.message });
        } else {
          console.log("Message from passport : ", info.message);
          res.status(403).send({ 'error': info.message });
        }
      } else {
        const token = jwt.sign({ id: user.id }, jwtSecret.secret);
        if (user.image == false) {
          console.log("No User Avatar Found !!!!");
          user.image = "";
        }
      //   firebase(user,{
      //     title: 'Welcome to DMS',
      //     message: 'Thanks for Logging In !!!!',
      //     timestamp: '2019-05-27 8:15:01'
      // });
        res.status(200).send({
          name: user.name,
          email: user.email,
          image: user.image,
          auth: true,
          token,
          message: 'user found & logged in',
        });
        console.log("Successful Login");
      }
    })(req, res, next);
  });
  server.post('/logout', (req, res, next) => {
    console.log("Doing logout");
    req.logout();
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
