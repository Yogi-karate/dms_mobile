const express = require('express');
const _ = require('lodash');
const logger = require('../../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../../odoo_server');
const search_model = require('../../models/search');

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

router.get('/search', async (req, res, next) => {
    try {
        console.log("the query params",req.query)
        let result = await search_model.find(req.user, req.query);
        res.json(result);
        next();
    } catch (err) {
        console.log(err.stack);
        res.json({ error: err.message || err.toString() });
        
    }
});



module.exports = router;
