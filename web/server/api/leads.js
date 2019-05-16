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
router.get('/search_counts', async (req, res) => {
  try {
    let result = await lead.getCounts(req.user);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/counts', async (req, res) => {
  try {
    console.log("user list >>>>", odoo.users);
    let server = odoo.getOdoo(req.user.email);
    console.log("Server: ", server);
    model = 'mail.activity';
    let result = await server.read_group(model, { domain: [["res_model", "=", "crm.lead"]], fields: ["name", "id", "state"], groupby: ["state"] });
    console.log(model + '', result);
    console.log(model + '...', result[0]);
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
module.exports = router;
