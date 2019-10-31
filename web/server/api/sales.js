const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');
const sale = require('../models/sale');

router.use((req, res, next) => {
    console.log("service api authenication ");
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
                console.log(info.message);
                res.status(403).send({ "error": info.message });
                return;
            }
            req.user = user;
            next();
        })(req, res, next);
    }
});


router.get('/dashboard', async (req, res) => {
    try {
        let result = await sale.getDashboardCounts(req.user);
        res.json(result);
    } catch (err) {
        res.json({ error: err.message || err.toString() });
    }
});
router.get('/search', async (req, res) => {
    try {
        let result = await sale.searchOrderByState(req.user, { state: req.query.state, invoice_status: req.query.invoice_status });
        res.json(result);
    } catch (err) {
        res.json({ error: err.message || err.toString() });
    }
});

router.get('/inventory/search', async (req, res) => {
    try {
        let result = await sale.searchInventoryByState(req.user, { state: req.query.state });
        res.json(result);
    } catch (err) {
        res.json({ error: err.message || err.toString() });
    }
});

router.get('/priceListItems', async (req, res) => {
    try {
        let result = await sale.priceListItem(req.user, { name: req.query.name });
        res.json(result);
    } catch (err) {
        res.json({ error: err.message || err.toString() });
    }
});

router.get('/saleOrderCountByState', async (req, res) => {
    try {
        let result = await sale.getOrderCountByState(req.user, { state: req.query.state });
        res.json({ "length": result });
    } catch (err) {
        res.json({ error: err.message || err.toString() });
    }
});

module.exports = router;
