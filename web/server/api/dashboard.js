const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');
const base = require('../models/base');
const User = require('../models/MUser');

router.use((req, res, next) => {
  console.log("dashboard api authenication ");
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
router.get('/inventory', async (req, res) => {
  try {
    model = 'stock.picking';
    console.log("Hello from inventory dashboard api");
    //fields: ["name", "id", "user_id", "team_id", "state", "scheduled_date", "picking_type_code"]
    let domain = [];
    domain.push(["state", "=", 'confirmed']);
    domain.push(["picking_type_id.code", "=", 'outgoing']);
    let result = await base.searchModels(req.user, {model, domain: domain});
    console.log(model + '', result);
    res.json({ "length": result});
  } catch (err) {
    console.log(err);
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/invoice', async (req, res) => {
  let result = null;
  try {
    let model = 'sale.order';
    let domain = [];
    domain.push(["invoice_status", "ilike", 'to invoice']);
    result = await base.searchModels(req.user, {model, domain: domain });
    console.log("The searchModels return result ", model + '', result);
    res.json({ "length": result});
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
module.exports = router;