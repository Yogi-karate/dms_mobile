const express = require('express');
const _ = require('lodash');
const logger = require('../../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../../odoo_server');
const base = require('../../models/base');
const User = require('../../models/MUser');

router.use((req, res, next) => {
  console.log("service api authenication  ");
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
        res.status(401).send("Unauthorized Access");
        return;
      }
      if (info !== undefined) {
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
router.get('/roles', async (req, res, next) => {
  try {
    let result = await base.getUserRole(req.user);
    res.json({ result });
  } catch (err) {
    next(err);
  }
});
router.post('/odoo/:model', async (req, res, next) => {
  try {
    let server = odoo.getOdoo(req.user.email);
    model = req.params.model;
    let result = await server.create(model, req.body);
    res.json({ "id": result, "Message": "Success" });
  } catch (err) {
    next(err);
  }
});
router.get('/odoo/:model', async (req, res, next) => {
  try {
    let server = odoo.getOdoo(req.user.email);
    model = req.params.model;
    let result = await server.search_read(model, { domain: [], fields: ["name", "id", "date_follow_up", "date_deadline", "partner_mobile", "mobile", "partner_name", "user_id", "team_id", "activity_state", "stage_id"], sort: "id desc" });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/odoo/:model/:id', async (req, res, next) => {
  try {
    let result = await base.getModel(req.user, { model: req.params.model, id: req.params.id });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.post('/odoo/:model/:id', async (req, res, next) => {
  console.log(req.user);
  try {
    let server = odoo.getOdoo(req.user.email);
    id = parseInt(req.params.id);
    model = req.params.model;
    // Get a partner
    let result = await server.update(model, id, req.body);
    res.json({ "success": result });
  } catch (err) {
    next(err);
  }
});
router.get('/search/products', async (req, res, next) => {
  console.log(req.user);
  try {
    let server = odoo.getOdoo(req.user.email);
    model = 'product.template';
    let result = await server.search_read(model, { domain: [["categ_id.name", "=", "Vehicles"]], fields: ["id", "name"] });
    res.json(result);
  } catch (err) {
    next(err);
  }
});

router.post('/search/colors', async (req, res, next) => {
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
    next(err);
  }
});
router.post('/search/variants', async (req, res, next) => {
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
    next(err);
  }
});
router.post('/search/variants_old', async (req, res, next) => {
  try {
    let server = odoo.getOdoo(req.user.email);
    variant_ids = req.body.variant_ids;
    console.log(variant_ids);
    model = 'product.attribute.value';
    let result = await server.search_read(model, { domain: [["id", "in", variant_ids]], fields: ["id", "name"] });
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.post('/register_token', async (req, res, next) => {
  try {
    let user = req.user;
    let result = await User.updateDeviceToken(user, req.body);
    res.json(result);
  } catch (err) {
    next(err);
  }
});
router.get('/updateCompany/:id', async (req, res, next) => {
  try {
    let roles = null;
    let companies = null;
    let result = {};
    let server = odoo.getOdoo(req.user.email);
    id = parseInt(req.params.id);
    model = 'res.users';
    // update user company
    let updatedCompany = await server.update(model, server.uid, { "company_id": id });
    roles = await base.getUserRole(req.user);
    companies = await base.getUserCompanies(req.user);
    result.role = roles.role; result.teams = roles.teams; result.module = roles.module; result.company_id = companies.company_id; result.company_ids = companies.company_ids;
    res.json(result);
  } catch (err) {
    next(err);
  }
});

module.exports = router;
