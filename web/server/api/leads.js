const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');
const lead = require('../models/lead');

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
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/stageCount', async (req, res) => {
  try {
    let result = await lead.getStageCounts(req.user);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/search', async (req, res) => {
  try {
    let result = await lead.searchLeadsByState(req.user, { state: req.query.state, stage: req.query.stage, userId: req.query.userId });
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
router.get('/leadDashboards/:id/:month/:year', async (req, res) => {
  try {
    let result = await lead.getLeadDashboards(req.user, { id: req.params.id }, { month: req.params.month }, { year: req.params.year });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/leadDashboard/:id', async (req, res) => {
  try {
    let result = await lead.getLeadDashboard(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/dailyLeadsNew/:team/:id/:month/:year', async (req, res) => {
  try {
    let result = await lead.getDailyLeadsNew(req.user, { team: req.params.team }, { id: req.params.id }, { month: req.params.month }, { year: req.params.year });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/dailyLeads/:id', async (req, res) => {
  try {
    let result = await lead.getDailyLeads(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/dailyBookedLeads/:id', async (req, res) => {
  try {
    let result = await lead.getDailyBookedLeads(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/paymentAccDetails', async (req, res) => {
  try {
    let result = await lead.getPaymentAccount(req.user);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/lostReasons', async (req, res) => {
  try {
    let result = await lead.lostReasons(req.user, { type: req.query.type });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
module.exports = router;
