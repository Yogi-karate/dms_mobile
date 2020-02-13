const express = require('express');
const session = require('express-session');
const next = require('next');
const helmet = require('helmet');
const getUser = require('./user');

require('dotenv').config();

const dev = process.env.NODE_ENV !== 'production';
const sessionSecret = process.env.SESSION_SECRET;
const port = process.env.PORT || 8003;
const ROOT_URL = dev ? `http://localhost:${port}` : 'https://dev.api.turnright.tech';
const API_URL = process.env.API_URL;
let URL_APP = ROOT_URL;
const app = next({ dev });
const handle = app.getRequestHandler();
// Nextjs's server prepared
app.prepare().then(() => {
    const server = express();
    server.get('/_next/*', (req, res) => {
        handle(req, res);
    });

    server.get('/static/*', (req, res) => {
        handle(req, res);
    });

    server.use(helmet());
    server.use(express.json());
    server.use(async (req, res, next) => {
        console.log("Calling middleware for get user");
        const headers = {};
        if (req.headers && req.headers.cookie) {
            console.log("Heaeders cookie",req.headers.cookie);
            headers.cookie = req.headers.cookie;
        }
        if (!req.user) {
            console.log("Hitting api for getting user details",headers);
            try {
                const user = await getUser({ headers },API_URL);
                req.user = user;
            } catch (error) {
                console.log(error);
            }
        } else {
            console.log("User is already there in request");
        }
        next();
    });

    server.get('/', async (req, res) => {
        let redirectUrl = 'login';

        if (req.user) {
            console.log("the user is present in req");
            redirectUrl = `dashboard`;
        }
        res.redirect(`${URL_APP}/${redirectUrl}`);
    });
    server.get('*', (req, res) => {
        handle(req, res);
    });
    // starting express server
    server.listen(port, (err) => {
        if (err) throw err;
        console.log(`> Ready on ${ROOT_URL}`); // eslint-disable-line no-console
    });
});
