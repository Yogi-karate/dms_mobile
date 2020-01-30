const express = require('express');
const _ = require('lodash');
const sms = require('../../../utils/sms_new');
const MUser = require('../../../models/core/MUser');
const passport = require('passport');
const logger = require('../../../logs');
const router = express.Router();
const odoo = require('../../../models/core/odoo_server');

router.use((req, res, next) => {
  console.log("service api authenication ");
  if (req.user) {
    next();
  } else {
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
      req.user = user;
      next();
    })(req, res, next);
  }
});


router.get('/sendapk', async (req, res, next) => {
  try {
    const users = await MUser.list();
    console.log("users list", users)
    let url = "https://play.google.com/store/apps/details?id=com.dealermanagmentsystem"
    apk_message = " Greetings Dear Saboo Employee , New version of Saboo DMS mobile app is available !! , please click here to upgrade  " + url;
    // let val = await sms('9840021822', apk_message.replace(/ /g, "%20"));
    let message = {};
    users.forEach(async function (user) {
      let mobile = user.mobile;
      let ret = await sms(mobile, apk_message.replace(/ /g, "%20"));
      message[user.name] = "Sent Message to " + user.mobile + ret;
    });
    console.log("Return from sms ", message);
    res.json(message);

  } catch (err) {
    next(err);
  }
});

module.exports = router;
