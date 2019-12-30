const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');
const base = require('../models/base');
const User = require('../models/MUser');
const task = require('../models/tasks');
const MsgTemplate = require('../models/MsgTemplate');
const JobMaster = require('../models/JobMaster');
const JobLog = require('../models/JobLog');
const smsJobs = require('../models/smsJobs');
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
    res.json({ new_user_count: new_users[0], updated_user_count: new_users[1], users });
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/sendServiceLeadSms', async (req, res) => {
  try {
    let result = await smsJobs.executeSMS(req.user, { callType: req.query.callType });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/sendInsuranceLeadSms', async (req, res) => {
  try {
    let result = await smsJobs.executeSMS(req.user, { callType: req.query.callType });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/sendServiceBookingSms', async (req, res) => {
  try {
    let result = await smsJobs.executeSMS(req.user, { callType: req.query.callType });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/sendInsuranceBookingSms', async (req, res) => {
  try {
    let result = await smsJobs.executeSMS(req.user, { callType: req.query.callType });
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

router.post('/createJobMaster', async (req, res) => {
  try {
    let result = await JobMaster.add(req.user, req.body);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/createJobLog', async (req, res) => {
  try {
    let result = await JobLog.add(req.body);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/listAdminUsers', async (req, res) => {
  try {
    let result = await User.listByAdmin();
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/listMsgTemplates/:name', async (req, res) => {
  try {
    let result = await MsgTemplate.list({ name: req.params.name });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/listJobMaster/:name', async (req, res) => {
  try {
    let result = await JobMaster.list(req.params.name);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/getCompanies', async (req, res) => {
  try {
    let result = await base.getUserCompanies(req.user);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/getJobLog/:id', async (req, res) => {
  try {
    let result = await JobLog.listById({ id: req.params.id });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
