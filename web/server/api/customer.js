const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');

router.use((req, res, next) => {
  next();
});
// router.use((req, res, next) => {
//   console.log("customer api authenication ");
//   passport.authenticate('jwt', { session: false }, (err, user, info) => {
//     if (err) {
//       console.error(err);
//       res.status(4011).send("Unauthorized Access");
//     }
//     if (info !== undefined) {
//       res.status(403).send(info.message);
//     } else {
//       res.status(200).send("You have succcesfully Authenticated");
//     }
//   })(req, res, next);
// });
router.post('/odoo/:model', async (req, res) => {  
  try {

    let server = odoo.getOdoo('admin');
    console.log("Server: ",server);
    model = req.params.model;
    server.create(model,req.body, function (err, order) {
      if (err) { return console.log(err); }
      console.log('Enquiry', order);
      console.log('Enquiry...', order[0]);
      res.json(order);
    });
    //});
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/odoo/:model', async (req, res) => {  
  try {

    let server = odoo.getOdoo('admin');
    console.log("Server: ",server);
    model = req.params.model;
    server.search_read(model,{domain:[],fields:["name","id","partner_name","user_id","team_id"]}, function (err, order) {
      if (err) { return console.log(err); }
      console.log('Enquiry', order);
      console.log('Enquiry...', order[0]);
      res.json(order);
    });
    //});
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/odoo/:model/:id', async (req, res) => {
  console.log("Session : ",req.session);
  try {
    // Connect to Odoo
    // odoo.connect(function (err) {
    //   if (err) { return console.log(err); }
    let server = odoo.getOdoo('admin');
    id = parseInt(req.params.id);
    model = req.params.model;
    // Get a partner
    server.get(model, id, function (err, order) {
      if (err) { return console.log(err); }
      console.log('Sale Order', order);
      console.log('Sale Ord', order[0]);
      res.json(order[0]);
    });
    //});
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
