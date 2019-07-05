const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');
const lead = require('../models/lead');

router.use((req, res, next) => {
  console.log("leads api authenication ");
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
router.get('/enquiry/:id', async (req, res) => {
  try {
    let result = await lead.getEnquiry(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/dashboard', async (req, res) => {
  try {
    let result = await lead.getDashboardCounts(req.user);
    console.log("Result ->" + '', result);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/search', async (req, res) => {
  try {
    let result = await lead.searchLeadsByState(req.user, { state: req.query.state, stage: req.query.stage });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/activity/:id', async (req, res) => {
  try {
    let result = await lead.getActivities(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/activity/complete', async (req, res) => {
  try {
    let id = parseInt(req.body.id);
    let result = await lead.setActivities(req.user, { id: id, feedback: req.body.feedback });
    res.json({ message: "success", id: result });
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/activity/create', async (req, res) => {
  try {
    let result = await lead.createActivity(req.user, req.body);
    res.json(result);
  } catch (err) {
  res.json({ error: err.message || err.toString() });
}
});
router.get('/leadDashboard/:id', async (req, res) => {
  try {
    console.log("The param id is ",req.params.id)
    let result = await lead.getLeadDashboard(req.user, {id: req.params.id });
    console.log("Result ->" + '', result);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/dailyLeads/:id', async (req, res) => {
  try {
    console.log("The param id is ",req.params.id)
    let result = await lead.getDailyLeads(req.user, {id: req.params.id });
    console.log("Result ->" + '', result);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/dailyBookedLeads/:id', async (req, res) => {
  try {
    console.log("The param id is ",req.params.id)
    let result = await lead.getDailyBookedLeads(req.user, {id: req.params.id });
    console.log("Result ->" + '', result);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
module.exports = router;
