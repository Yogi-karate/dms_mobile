const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');
const base = require('../models/base');
const User = require('../models/MUser');

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
    console.log(odoo.users);
    console.log(user);
    req.user = user;
    next();
  })(req, res, next);
});
router.post('/odoo/:model', async (req, res) => {
  try {
    console.log(req.user);
    let server = odoo.getOdoo(req.user.email);
    console.log("Server: ", server);
    model = req.params.model;
    let result = await server.create(model, req.body);
    console.log(model, result);
    console.log(model + '...', result[0]);
    res.json({ "id": result, "Message": "Success" });
  } catch (err) {
  res.json({ error: err.message || err.toString() });
}
});
router.get('/odoo/:model', async (req, res) => {
  try {
    console.log("user list >>>>", odoo.users);
    let server = odoo.getOdoo(req.user.email);
    console.log("Server: ", server);
    model = req.params.model;
    let result = await server.search_read(model, { domain: [], fields: ["name", "id", "date_follow_up", "date_deadline", "partner_mobile", "mobile", "partner_name", "user_id", "team_id", "activity_state", "stage_id"], sort: "id desc" });
    console.log(model + '', result);
    console.log(model + '...', result[0]);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/odoo/:model/:id', async (req, res) => {
  try {
    let result = await base.getModel(req.user, { model: req.params.model, id: req.params.id });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/odoo/:model/:id', async (req, res) => {
  console.log(req.user);
  try {
    let server = odoo.getOdoo(req.user.email);
    id = parseInt(req.params.id);
    model = req.params.model;
    // Get a partner
    server.update(model, id, req.body, function (err, result) {
      if (err) { return console.log(err); }
      console.log(model, result);
      console.log(model, result[0]);
      let model_new = result[0];
      res.json({ "success": result });
    });
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/search/products', async (req, res) => {
  console.log(req.user);
  try {
    let server = odoo.getOdoo(req.user.email);
    model = 'product.template';
    let result = await server.search_read(model, { domain: [["categ_id.name", "=", "Vehicles"]], fields: ["id", "name"] });
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/search/colors', async (req, res) => {
  console.log(req.user);
  try {
    let server = odoo.getOdoo(req.user.email);
    product_id = parseInt(req.body.product_id);
    variant_id = req.body.variant_id;
    model = 'product.product';
    let result = await server.search_read(model, { domain: [["product_tmpl_id", "=", product_id], ["attribute_value_ids", "in", variant_id]], fields: ["id", "attribute_value_ids"] });
    let value_ids = []
    result['records'].forEach(product => {
      attr_val_ids = product["attribute_value_ids"];
      value_ids = _.union(attr_val_ids, value_ids);
      console.log("the value_ids", value_ids);
    });
    model = "product.attribute.value"
    let result_1 = await server.search_read(model, { domain: [["id", "in", value_ids], ["attribute_id.name", "ilike", "color"]], fields: ["id", "display_name"] });
    res.json(result_1);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/search/variants', async (req, res) => {
  console.log(req.user);
  try {
    let server = odoo.getOdoo(req.user.email);
    product_id = parseInt(req.body.product_id);
    model = 'product.product';
    let result = await server.search_read(model, { domain: [["product_tmpl_id", "=", product_id]], fields: ["id", "attribute_value_ids"] });
    console.log(model + '', result);
    console.log(model + '...', result[0]);
    let value_ids = []
    result['records'].forEach(product => {
      attr_val_ids = product["attribute_value_ids"];
      value_ids = _.union(attr_val_ids, value_ids);
      console.log("the value_ids", value_ids);
    });
    model = "product.attribute.value"
    let result_1 = await server.search_read(model, { domain: [["id", "in", value_ids], ["attribute_id.name", "ilike", "variant"]], fields: ["id", "display_name"] });
    res.json(result_1);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/search/variants_old', async (req, res) => {
  console.log(req);
  try {
    let server = odoo.getOdoo(req.user.email);
    variant_ids = req.body.variant_ids;
    console.log(variant_ids);
    model = 'product.attribute.value';
    let result = await server.search_read(model, { domain: [["id", "in", variant_ids]], fields: ["id", "name"] });
    console.log(model + '', result);
    console.log(model + '...', result[0]);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/register_token', async (req, res) => {
  console.log(req.body);
  try {
    let user = req.user;
    console.log(user);
    let result = await User.updateDeviceToken(user,req.body);
    console.log( user.name + '', result);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
