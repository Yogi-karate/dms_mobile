const express = require('express');
const _ = require('lodash');
const sms = require('../ext/sms');
const MUser = require('../models/MUser');
const passport = require('passport');
const logger = require('../logs');
const router = express.Router();
const odoo = require('../odoo_server');

router.use((req, res, next) => {
    console.log("customer api authenication ");
    passport.authenticate('jwt', { session: false }, (err, user, info) => {
      if (err) {
        console.error(err);
        res.status(401).send("Unauthorized Access");
        return;
      }
      if (info !== undefined) {
        console.log(req);
        console.log(info.message);
        res.status(403).send({ "error": info.message });
        return;
      }
      console.log(odoo.users);
      console.log(user);
      req.user = user;
      next();
    })(req, res, next);
  });

router.get('/sendapk', async (req, res) => {
  try {
    const users = await MUser.list();
    console.log(users);
    let url = "https://tinyurl.com/yxsxyvyk"
    apk_message = " Greetings Dear Saboo Employee !!!!! Please click here to download the Saboo DMS mobile app " + url;
    //let val = await sms('9840021822', apk_message.replace(/ /g, "%20"));
    let message={};
    users['users'].forEach(async function(user) {
      let mobile = user.mobile;
      console.log(mobile);
      let ret = await sms(mobile, apk_message.replace(/ /g, "%20"));
      message[user.name] = "Sent Message to "+user.mobile + ret;
    });
    console.log("Return from sms " ,message);
    res.json(message);

  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
