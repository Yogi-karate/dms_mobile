const dotenvRes = require('dotenv').config();

module.exports = {
  // next.config.js
  serverRuntimeConfig: {
    // Will only be available on the server side
    mySecret: 'secret',
    secondSecret: process.env.SECOND_SECRET, // Pass through env variables
  },
  publicRuntimeConfig: {
    // Will be available on both server and client
    port:process.env.PORT,
    env:process.env.NODE_ENV,
    host:process.env.HOST_URL,
  },
}