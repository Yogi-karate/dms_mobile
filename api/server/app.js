const express = require('express');
const session = require('express-session');
const mongoSessionStore = require('connect-mongo');
const mongoose = require('mongoose');
const auth_pass = require('./passport');
const api_v1 = require('./api/v1');
const api_v2 = require('./api/v2');
const odoo = require('./models/core/odoo_server')
const cors = require('cors');
const AWS = require('aws-sdk');

// const admin = require('firebase-admin');
// const firebaseAccount = require("../firebase_dms.json");
const logger = require('./logs');

require('dotenv').config();

const dev = process.env.NODE_ENV !== 'production';
const MONGO_URL = process.env.MONGO_URL_TEST;

mongoose.connect(MONGO_URL, { useNewUrlParser: true });

const port = process.env.PORT || 8000;
const ROOT_URL = dev ? `http://localhost:${port}` : 'http://dev.api.turnright.tech';
const APP_URL = dev ? process.env.APP_URL : 'http://dashboard.saboo.group';

const sessionSecret = process.env.SESSION_SECRET;

const URL_MAP = {
    '/login': '/public/login',
};

const server = express();

//server.use(express.json());
server.use(express.json({limit: '50mb'}));
server.use(express.urlencoded({limit: '50mb'}));

logger.stream = {
    write: function (message, encoding) {
        logger.info(message);
    }
};
//server.use(cors({ origin: APP_URL, credentials: true }));
server.use(require("morgan")("combined", { "stream": logger.stream }));

// confuring MongoDB session store
const MongoStore = mongoSessionStore(session);
const sess = {
    name: 'dms.sid',
    secret: sessionSecret,
    store: new MongoStore({
        mongooseConnection: mongoose.connection,
        ttl: 14 * 24 * 60 * 60, // save session 14 days
    }),
    resave: false,
    saveUninitialized: false,
    cookie: {
        secure:false,
        httpOnly: false,
        maxAge: 14 * 24 * 60 * 60 * 1000,
    },
};
//configuring the AWS environment
AWS.config.update({
    accessKeyId: process.env.Amazon_accessKeyId,
    secretAccessKey: process.env.Amazon_secretAccessKey
  });
server.use(session(sess));
auth_pass({ server });
api_v1(server);
api_v2(server);

// admin.initializeApp({
//     credential: admin.credential.cert(firebaseAccount),
//     databaseURL: "https://dealer-managment-system.firebaseio.com"
// });

// starting express server
odoo.init().then(() => {
    server.listen(port, (err) => {
        if (err) throw err;
        console.log(`> Ready on ${ROOT_URL}`); // eslint-disable-line no-console
    });
console.log(require('util').inspect(odoo));
}).catch(() => {
    console.log("Error in setting up server");
    process.exit(1);
});
