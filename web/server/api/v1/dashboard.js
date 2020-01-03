const express = require('express');
const _ = require('lodash');
const logger = require('../../logs');
const router = express.Router();
const passport = require('passport');
const base = require('../../models/base');
const sale = require('../../models/sale');

router.use((req, res, next) => {
  console.log("dashboard api authenication ");
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
              console.log(req);
              console.log(info.message);
              res.status(403).send({ "error": info.message });
              return;
          }
          console.log(user);
          req.user = user;
          next();
      })(req, res, next);
  }
});

router.get('/inventory', async (req, res) => {
  try {
    model = 'stock.picking';
    let domain = [];
    domain.push(["state", "=", 'confirmed']);
    domain.push(["picking_type_id.code", "=", 'outgoing']);
    let result = await base.searchModels(req.user, {model, domain: domain});
    res.json({ "length": result});
  } catch (err) {
    console.log(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/inventory/stock', async (req, res) => {
  try {
    model = 'stock.picking';
    let domain = [];
    let fields = ["name","origin","state"];
    let result = await sale.getInventoryStock(req.user, {model, domain: domain, fields: fields});
    res.json({result});
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
    res.json({ "length": result});
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
module.exports = router;