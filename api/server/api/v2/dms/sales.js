const express = require('express');
const _ = require('lodash');
const logger = require('../../../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../../../models/core/odoo_server');
const sale = require('../../../models/dms/sale');

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


router.get('/dashboard', async (req, res, next) => {
    try {
        let result = await sale.getDashboardCounts(req.user);
        res.json(result);
    } catch (err) {
        next(err);
    }
});
router.get('/search', async (req, res, next) => {
    try {
        let result = await sale.searchOrderByState(req.user, { state: req.query.state, invoice_status: req.query.invoice_status, leadId: req.query.leadId });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

router.get('/inventory/search', async (req, res, next) => {
    try {
        let result = await sale.searchInventoryByState(req.user, { state: req.query.state });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

router.get('/priceListItems', async (req, res, next) => {
    try {
        let result = await sale.priceListItem(req.user, { name: req.query.name });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

router.get('/saleOrderCountByState', async (req, res, next) => {
    try {
        let result = await sale.getOrderCountByState(req.user, { state: req.query.state });
        res.json({ "length": result });
    } catch (err) {
        next(err);
    }
});

router.get('/saleOrderPrice', async (req, res, next) => {
    try {
        let result = await sale.saleOrderPrice(req.user, { orderId: req.query.orderId });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

router.get('/saleOrderCustomer', async (req, res, next) => {
    try {
        let result = await sale.saleOrderCustomer(req.user, { orderId: req.query.orderId });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

router.get('/quotationCount', async (req, res, next) => {
    try {
        let result = await sale.quotationCount(req.user);
        res.json({ "length": result });
    } catch (err) {
        next(err);
    }
});

router.get('/confirmQuotation', async (req, res, next) => {
    try {
        let result = await sale.confirmQuotation(req.user, { orderId: req.query.orderId });
        if (result == false) {
            res.json({ message: "success" });
        } else {
            res.status(500).send({ message: "failed" });
        }
    } catch (err) {
        next(err);
    }
});

router.get('/orderPaymentDetails', async (req, res, next) => {
    try {
        let result = await sale.saleOrderPaymentDetails(req.user, { orderId: req.query.orderId });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

router.get('/orderBookingDetails', async (req, res, next) => {
    try {
        let result = await sale.updateOrderBookingDetails(req.user, { orderId: req.query.orderId });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

module.exports = router;
