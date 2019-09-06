const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');
const base = require('../models/base');
const User = require('../models/MUser');
const task = require('../models/tasks');
const lead = require('../models/lead');
const vehicleLead = require('../models/vehicleLead');
const MsgLog = require('../models/MsgLog');
const JobLog = require('../models/JobLog');
const MsgTemplate = require('../models/MsgTemplate');

const sms = require('../ext/sms_new');


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
        console.log(info.message);
        res.status(403).send({ "error": info.message });
        return;
      }
      req.user = user;
      next();
    })(req, res, next);
  }
});

router.get('/notifications', async (req, res) => {
  try {
    let result = {};
    let users = await User.list();
    users.users.forEach(async function (user) {
      result[user.email] = await task.sendTaskNotification(req.user, user);
      console.log("Result ->" + '', result[user.email]);
    });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/sendapk', async (req, res) => {
  try {
    const users = await User.list();
    console.log(users);
    let url = req.body.url;
    apk_message = req.body.message + " Click here: " + url;
    let message = {};
    users['users'].forEach(async function (user) {
      let mobile = user.mobile;
      console.log(mobile);
      let ret = await sms(mobile, apk_message.replace(/ /g, "%20"));
      message[user.name] = "Sent Message to " + user.mobile + ret;
    });
    res.json(message);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/users', async (req, res) => {
  try {
    let action = req.query.action;
    let new_users = 0;
    if (action == "refresh") {
      new_users = await odoo.refreshUsers(odoo.getOdoo(req.user.email));
    }
    let users = await User.list();
    res.json({ new_user_count: new_users, users });
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/sendLeadSms', async (req, res) => {
  try {
    let result = await lead.getLeadDetails(req.user, { callType: req.query.callType });
    let message = {};
    let smsCount = 0;
    let messageStatus = "";
    if (result.records !== null) {
      result.records.forEach(async function (record) {
        let mobile = record.mobile;
        if (mobile === "9849927027") {
          let mobile = "7795659269";
          console.log("The mobile isssss ", mobile);
          let ret = await sms(mobile, "Dear customer your vehicle is due for service on date");
          console.log("The ret is ", ret);
          message = "Sent Message to " + mobile + " " + ret;
          if (ret === 'failure') {
            messageStatus = 'failure';
          }
          let NewMsgLog = { name: record.partner_name, mobile: record.mobile, sms_type: 'leadSms', message: message, response: ret }
          let newMsgLogs = await MsgLog.add(NewMsgLog);
        }
        smsCount += 1;
      });
    }
    if (smsCount !== 0 && messageStatus !== "failure") {
      let NewJobLog = { smsCount: smsCount, status: "completed" };
      let newJobLogs = await JobLog.add(NewJobLog);
    } else if (messageStatus === 'failure') {
      let NewJobLog = { smsCount: smsCount, status: "failure" };
      let newJobLogs = await JobLog.add(NewJobLog);
    }
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/sendBookingSms', async (req, res) => {
  try {
    let message = {};
    let smsCount = 0;
    let messageStatus = "";
    let result = await vehicleLead.serviceBookingDetails(req.user, { callType: req.query.callType });
    if (result.result.records !== null) {
      result.result.records.forEach(async function (record) {
        let mobile = record.mobile;
        if (mobile === '9885008580') {
          mobile = '7795659269';
          console.log("The mobile sendBookingSms ", mobile);
          let messageResponse = await sms(mobile, "Your booking is confirmed, Thanks for booking with us!!!");
          message = "Sent Message to " + mobile + " " + messageResponse;
          if (messageResponse === 'failure') {
            messageStatus = 'failure';
          }
          let NewMsgLog = { name: record.partner_name, mobile: record.mobile, sms_type: 'bookingSms', message: message, response: messageResponse }
          let newMsgLogs = await MsgLog.add(NewMsgLog);
          console.log("The newMsgLogs ", newMsgLogs);
        }
        smsCount += 1;
      });
    }
    if (smsCount !== 0 && messageStatus !== "failure") {
      let NewJobLog = { smsCount: smsCount, status: "completed" };
      let newJobLogs = await JobLog.add(NewJobLog);
    } else if (messageStatus === 'failure') {
      let NewJobLog = { smsCount: smsCount, status: "failure" };
      let newJobLogs = await JobLog.add(NewJobLog);
    }
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/createMsgTemplate', async (req, res) => {
  try {
    let result = await MsgTemplate.add(req.user, req.body);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
