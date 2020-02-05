const express = require('express');
const _ = require('lodash');
const logger = require('../../../logs');
const router = express.Router();
const passport = require('passport');
const odoo = require('../../../models/core/odoo_server');
const base = require('../../../models/core/base');
const User = require('../../../models/core/MUser');

router.use((req, res, next) => {
    console.log("customer api authenication ");
    if (req.user) {
        console.log('the path of the request is ', req.path);
        next();
    } else {
        passport.authenticate('jwt', { session: false }, (err, user, info) => {
            if (err) {
                console.error(err);
                res.status(401).send("Unauthorized Access");
                return;
            }
            if (info !== undefined) {
                console.log(req);
                console.log(info.message);
                res.status(403).send({ "error": info.message });
                return;
            }
            console.log(user);
            req.user = user;
            next();
        })(req, res, next);
    }
});
router.get('/user', async (req, res, next) => {
    try {
        console.log("the user is",req.user);
        res.json( req.user );
    } catch (err) {
        next(err);
    }
});


router.get('/roles', async (req, res, next) => {
    try {
        let result = await base.getUserRole(req.user);
        res.json({ result });
    } catch (err) {
        next(err);
    }
});

router.post('/register_token', async (req, res, next) => {
    try {
        let user = req.user;
        let result = await User.updateDeviceToken(user, req.body);
        res.json(result);
    } catch (err) {
        next(err);
    }
});

router.get('/updateCompany/:id', async (req, res, next) => {
    try {
        let roles = null;
        let companies = null;
        let result = {};
        let server = odoo.getOdoo(req.user.email);
        id = parseInt(req.params.id);
        model = 'res.users';
        // update user company
        let updatedCompany = await server.update(model, server.uid, { "company_id": id });
        roles = await base.getUserRole(req.user);
        companies = await base.getUserCompanies(req.user);
        result.role = roles.role; result.teams = roles.teams; result.module = roles.module; result.company_id = companies.company_id; result.company_ids = companies.company_ids;
        res.json(result);
    } catch (err) {
        next(err);
    }
});

module.exports = router;