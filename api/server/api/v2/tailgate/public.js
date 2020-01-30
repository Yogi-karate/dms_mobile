const express = require('express');
const _ = require('lodash');
const logger = require('../../../logs');
const router = express.Router();
const inventory = require('../../../models/tailgate/inventory');
const store = require('../../../ext/s3');


router.use((req, res, next) => {
  console.log("service api authenication ");
  next();
});

router.get('/inventory/stock', async (req, res, next) => {
  try {
    model = 'stock.picking';
    console.log("Hello from inventory stock api");
    let domain = [];
    let fields = ["name", "origin", "state"];
    let result = await sale.getInventoryStock(req.user, { model, domain: domain, fields: fields });
    console.log(model + '', result);
    res.json({ result });
  } catch (err) {
    console.log(err);
    next(err);
    //res.json({ error: err.message || err.toString() });
  }
});
router.get('/s3', async (req, res, next) => {
  try {
    console.log("Hello from s3 ---CALL");
    let result = await store.s3("./static/Saboo-02.png");
    console.log(result);
    res.json({ result });
  } catch (err) {
    console.log(err);
    next(err);
    //res.json({ error: err.message || err.toString() });
  }
});

/* middleware to handle errors (this should be present at the end of all api's always) */
router.use((err, req, res, next) => {
  res.status(err.status || 500);
  res.json({
    message: err.message
  });
});

module.exports = router;
