const express = require('express');
const _ = require('lodash');
const logger = require('../../../logs');
const router = express.Router();
const passport = require('passport');
const eventmasters = require('../../../models/tailgate/EventMaster');

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

router.post('/createEventMaster', async (req, res, next) => {
    try {
        let resultJobMasters = await eventmasters.add(req.user, req.body);
        res.json(resultJobMasters);
    } catch (err) {
        next(err);
    }
});

router.get('/listEventMaster', async (req, res, next) => {
    try {
        let listEventMasters = await eventmasters.list(req.user);
        res.json(listEventMasters);
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
