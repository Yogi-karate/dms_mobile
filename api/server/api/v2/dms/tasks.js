const express = require('express');
const _ = require('lodash');
const logger = require('../../../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../../../models/core/odoo_server');
const lead = require('../../../models/dms/lead');
const task = require('../../../models/dms/tasks');

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

router.get('/notifications', async (req, res, next) => {
  try {
    let result = await task.sendTaskNotification(req.user);
    console.log("Result ->" + '', result);
    res.json(result);
  } catch (err) {
    next(err);
  }
});

router.get('/list', async (req, res, next) => {
  try {
    let result = await task.getUserTasks(req.user, { modelName: req.params.modelName });
    console.log("Result ->" + '', result);
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/:model/:id', async (req, res, next) => {
  try {
    let result = await task.getActivities(req.user, { id: req.params.id, modelName: req.params.model });
    console.log("Result ->" + '', result);
    res.json(result);
  } catch (err) {
    next(err);
  }
});

module.exports = router;
