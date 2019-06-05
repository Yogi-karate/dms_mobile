const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');
const base = require('../models/base');
const User = require('../models/MUser');
const task = require('../models/tasks');

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
    if (user.email !== "admin") {
        res.status(403).send({ "error": "Admin Only !!!" });
        return;
      }
    console.log(odoo.users);
    console.log(user);
    req.user = user;
    next();
  })(req, res, next);
});
router.get('/notifications', async (req, res) => {
    try {
      let result = {};  
      let users = await User.list();
     // console.log("users ",users);
      users.users.forEach(async function (user) {
        result[user.email] = await task.sendTaskNotification(req.user,user);
        console.log("Result ->" + '', result[user.email]);
      });
    //  console.log("Result ->" + '', result);
      res.json(result);
    } catch (err) {
      res.json({ error: err.message || err.toString() });
    }
  });

  module.exports = router;
