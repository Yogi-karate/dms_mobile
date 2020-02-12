const express = require('express');
const _ = require('lodash');
const logger = require('../../../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../../../models/core/odoo_server');
const service = require('../../../models/dms/service');

router.use((req, res, next) => {
    console.log("serviceeeeee api authenication ");
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
                res.status(401).send("Unauthorized Access ");
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

        console.log("In dashboard call ------", service);
        let result = await service.getDashboardCounts(req.user, { callType: req.query.callType });
        res.json(result);
    } catch (err) {
        next(err);
    }
});
router.get('/search', async (req, res, next) => {
    try {

        console.log("In dashboard call ------", service);
        let result = await service.searchBookings(req.user, { status: req.query.status });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

router.get('/serviceBookingCount', async (req, res, next) => {
    try {
        let result = await service.serviceBookingCount(req.user, { callType: req.query.callType });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

router.get('/serviceBookingDetails', async (req, res, next) => {
    try {
        let result = await service.serviceBookingDetails(req.user, { callType: req.query.callType });
        res.json(result);
    } catch (err) {
        next(err);
    }
});

module.exports = router;
