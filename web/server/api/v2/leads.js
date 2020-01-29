const express = require('express');
const _ = require('lodash');
const logger = require('../../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../../odoo_server');
const lead = require('../../models/lead');

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

router.get('/enquiry/:id', async (req, res, next) => {
  try {
    let result = await lead.getEnquiry(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/dashboard', async (req, res, next) => {
  try {
    let result = await lead.getDashboardCounts(req.user);
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/stageCount', async (req, res, next) => {
  try {
    let result = await lead.getStageCounts(req.user);
    res.json(result);
  } catch (err) {
    next(err);
  }
});

router.get('/search', async (req, res, next) => {
  try {
    let result = await lead.searchLeadsByState(req.user, { state: req.query.state, stage: req.query.stage, userId: req.query.userId });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/activity/:id', async (req, res, next) => {
  try {
    let result = await lead.getActivities(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.post('/activity/complete', async (req, res, next) => {
  try {
    let id = parseInt(req.body.id);
    let result = await lead.setActivities(req.user, { id: id, feedback: req.body.feedback });
    res.json({ message: "success", id: result });
  } catch (err) {
    next(err);
  }
});
router.post('/activity/create', async (req, res, next) => {
  try {
    let result = await lead.createActivity(req.user, req.body);
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/leadDashboards/:id/:month/:year', async (req, res, next) => {
  try {
    let result = await lead.getLeadDashboards(req.user, { id: req.params.id }, { month: req.params.month }, { year: req.params.year });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/leadDashboard/:id', async (req, res, next) => {
  try {
    let result = await lead.getLeadDashboard(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/dailyLeadsNew/:team/:id/:month/:year', async (req, res, next) => {
  try {
    let result = await lead.getDailyLeadsNew(req.user, { team: req.params.team }, { id: req.params.id }, { month: req.params.month }, { year: req.params.year });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/dailyLeads/:id', async (req, res, next) => {
  try {
    let result = await lead.getDailyLeads(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/dailyBookedLeads/:id', async (req, res, next) => {
  try {
    let result = await lead.getDailyBookedLeads(req.user, { id: req.params.id });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/paymentAccDetails', async (req, res, next) => {
  try {
    let result = await lead.getPaymentAccount(req.user);
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/lostReasons', async (req, res, next) => {
  try {
    let result = await lead.lostReasons(req.user, { type: req.query.type });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.post('/quotation/create', async (req, res, next) => {
  try {
    let result = await lead.createQuotation(req.user, req.body);
    if (result == false) {
      res.json({ message: "success" });
    } else {
      res.status(500).send({ message: "failed" });
    }
  } catch (err) {
    next(err);
  }
});
router.get('/leadDetails', async (req, res, next) => {
  try {
    let result = await lead.leadDetails(req.user, { leadId: req.query.leadId });
    res.json(result);
  } catch (err) {
    next(err);
  }
});

module.exports = router;

