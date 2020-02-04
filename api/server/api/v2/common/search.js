const express = require('express');
const _ = require('lodash');
const logger = require('../../../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../../../models/core/odoo_server');
const base = require('../../../models/core/base');
const User = require('../../../models/core/MUser');

router.use((req, res, next) => {
  console.log("customer api authenication ");
  if (req.user) {
    console.log('the path of the request is ',req.path);
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

router.get('/products', async (req, res, next) => {
 // console.log(req.user);
  try {
    
    let server = odoo.getOdoo(req.user.email);
    model = 'product.template';
    let result = await server.search_read(model, { domain: [["categ_id.name", "=", "Vehicles"]], fields: ["id", "name"] });
    res.json(result);
  } catch (err) {
    console.log(err.stack);
    next(err);
  }
});

router.post('/colors', async (req, res, next) => {
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
router.post('/variants', async (req, res, next) => {
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
router.post('/variants_old', async (req, res, next) => {
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

module.exports = router;
