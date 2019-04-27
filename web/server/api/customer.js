const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');

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
      res.status(403).send({"error":info.message});
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
    let server = odoo.getOdoo(req.user.name);
    console.log("Server: ", server);
    model = req.params.model;
    server.create(model, req.body, function (err, order) {
      if (err) { return console.log(err); }
      console.log(model, order);
      console.log(model+'...', order[0]);
      res.json({ "id": order, "Message": "Success" });
    });
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/odoo/:model', async (req, res) => {
  try {
    console.log("user list >>>>", odoo.users);
    let server = odoo.getOdoo(req.user.name);
    console.log("Server: ", server);
    model = req.params.model;
    let result = await server.search_read(model, { domain: [], fields: ["name", "id", "partner_name", "user_id", "team_id"], sort: "id desc" });
    console.log(model + '', result);
    console.log(model + '...', result[0]);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/odoo/:model/:id', async (req, res) => {
  console.log("Session : ", req.session);
  console.log(req.user);
  try {
    let server = odoo.getOdoo(req.user.name);
    id = parseInt(req.params.id);
    model = req.params.model;
    // Get a partner
    server.get(model, id, function (err, result) {
      if (err) { return console.log(err); }
      console.log(model, result);
      console.log(model, result[0]);
      let model_new = result[0];
      if (model_new != null) {
        keys = Object.keys(model_new);
        keys.forEach(key => {
          if (model_new[key] === false) {
            model_new[key] = "";
          }
        });
      }
      res.json(model_new);
    });
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/odoo/:model/:id', async (req, res) => {
  console.log(req.user);
  try {
    let server = odoo.getOdoo(req.user.name);
    id = parseInt(req.params.id);
    model = req.params.model;
    // Get a partner
    server.update(model, id, req.body,function (err, result) {
      if (err) { return console.log(err); }
      console.log(model, result);
      console.log(model, result[0]);
      let model_new = result[0];
      res.json({"success":result});
    });
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/search/colors', async (req, res) => {
  console.log(req.user);
  try {
    let server = odoo.getOdoo(req.user.name);
    product_id = parseInt(req.body.product_id);
    model = 'product.product';
    let result = await server.search(model,{domain:[["product_tmpl_id","=",product_id]] });
    console.log(model + '', result);
    console.log(model + '...', result[0]);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.post('/search/variants', async (req, res) => {
  console.log(req);
  try {
    let server = odoo.getOdoo(req.user.name);
    variant_ids = req.body.variant_ids;
    console.log(variant_ids);
    model = 'product.attribute.value';
    let result = await server.search_read(model,{domain:[["id","in",variant_ids]],fields:["id","name"]});
    console.log(model + '', result);
    console.log(model + '...', result[0]);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
module.exports = router;
