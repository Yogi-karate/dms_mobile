const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../odoo_server');
const stock = require('../models/stock');

router.use((req, res, next) => {
    console.log("service api authenication ");
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

router.get('/vehicles', async (req, res) => {
    try {
        let result = await stock.vehicleList(req.user);
        console.log("Inside stock vehicles api ", result);
        res.json(result);
    } catch (err) {
        res.json({ error: err.message || err.toString() });
    }
});

module.exports = router;