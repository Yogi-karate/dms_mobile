const express = require('express');
const _ = require('lodash');
const logger = require('../../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../../odoo_server');
const vehicleLead = require('../../models/vehicleLead');

router.use((req, res, next) => {
  console.log("service api authenication ");
  if (req.user) {
    server = odoo.getOdoo(req.user.email);
    if (server.sid == null) {
      console.error("Not connected to backend !!!!!");
      res.status(401).send("Unauthorized Access");
      return;
    } else next();

  } else {
    passport.authenticate('jwt', { session: false }, (err, user, info) => {
      if (err) {
        console.error(err);
        res.status(401).send("Unauthorized Access ");
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
router.get('/dashboard', async (req, res) => {
  try {
    let result = await vehicleLead.getDashboardCounts(req.user, { callType: req.query.callType });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/search', async (req, res) => {
  try {
    let result = await vehicleLead.searchLeadsByState(req.user, { state: req.query.state, callType: req.query.callType, userId: req.query.userId });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/activity/:id', async (req, res) => {
  try {
    let result = await vehicleLead.getActivities(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/activity/complete', async (req, res) => {
  try {
    let id = parseInt(req.body.id);
    let result = await vehicleLead.setActivities(req.user, { id: id, feedback: req.body.feedback, disposition_id: req.body.disposition_id});
    res.json({ message: "success", id: result });
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/activity/create', async (req, res) => {
  try {
    let result = await vehicleLead.createActivity(req.user, req.body);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/serviceBookingCount', async (req, res) => {
  try {
    let result = await vehicleLead.serviceBookingCount(req.user, { callType: req.query.callType });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/serviceBookingDetails', async (req, res) => {
  try {
    let result = await vehicleLead.serviceBookingDetails(req.user, { callType: req.query.callType });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
