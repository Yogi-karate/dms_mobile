const express = require('express');
const session = require('express-session');
const mongoSessionStore = require('connect-mongo');
const mongoose = require('mongoose');
const auth_pass = require('./passport');
const api = require('./api');
require('dotenv').config();

const dev = process.env.NODE_ENV !== 'production';
const port = process.env.PORT || 8000;
const ROOT_URL = dev ? `http://localhost:${port}` : 'https://dev.api.turnright.tech';
const sessionSecret = process.env.SESSION_SECRET;

const MONGO_URL = process.env.MONGO_URL_TEST;
mongoose.connect(MONGO_URL, { useNewUrlParser: true });

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
    httpOnly: true,
    maxAge: 14 * 24 * 60 * 60 * 1000,
  },
};
const server = express();
server.use(express.json());
server.use(session(sess));
auth_pass({ server });
api(server);
// starting express server
server.listen(port, (err) => {
    if (err) throw err;
    console.log(`> Ready on ${ROOT_URL}`); // eslint-disable-line no-console
});

