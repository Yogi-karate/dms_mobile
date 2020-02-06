const express = require('express');
const _ = require('lodash');

const LocationRegistration = require('../../../models/tailgate/LocationRegistration');
const base = require('../../../models/core/base');
const logger = require('../../../logs');
const router = express.Router();
const passport = require('passport');

router.use((req, res, next) => {
    console.log("service api authentication ");
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

router.get('/listLocationRegistrations', async (req, res, next) => {
    try {
        console.log("Inside list location reg api");
        const LocationRegs = await LocationRegistration.list();
        res.json(LocationRegs);
    } catch (err) {
        next(err);
    }
});

router.get('/getLocation/:locationId', async (req, res, next) => {
    try {
        console.log("Inside get location based on id api");
        const LocationRegs = await LocationRegistration.listById(req.params.locationId);
        res.json(LocationRegs);
    } catch (err) {
        next(err);
    }
});

router.get('/listSecurityLocations', async (req, res, next) => {
    try {
        console.log("Inside listSecurityLocations reg api");
        let userId = req.user._id;
        const LocationRegs = await LocationRegistration.listBySecurity(userId);
        res.json(LocationRegs);
    } catch (err) {
        next(err);
    }
});

router.post('/updateLocationRegistrations', async (req, res, next) => {
    try {
        console.log("Inside update location reg api");
        const LocationRegs = await LocationRegistration.update({ locationId: req.query.locationId }, req.body);
        res.json({ "message": "updated successful" });
    } catch (err) {
        next(err);
    }
});

router.post('/createLocationRegistration', async (req, res, next) => {
    try {
        console.log("Inside create Location reg api");
        const createdLocationResult = await LocationRegistration.add(req.body);
        res.json({ "message": createdLocationResult });
    } catch (err) {
        next(err);
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