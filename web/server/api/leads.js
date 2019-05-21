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
    let result = await lead.getEnquiry(req.user,{id:req.params.id});
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/dashboard', async (req, res) => {
  try {
    let result = await lead.getDashboardCounts(req.user);
    console.log( "Result ->"+ '', result);
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
router.get('/activity/complete/:id', async (req, res) => {
  try {
    let id = parseInt(req.params.id);
    let result = await lead.setActivities(req.user, { id:id,feedback:"Hello World !!!" });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
module.exports = router;
