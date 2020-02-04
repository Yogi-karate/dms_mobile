const customerApi = require('./customer');
const searchApi = require('./search');
const userApi = require('./user');

const logger = require('../../../logs');

function handleError(err, req, res, next) {
  logger.error(err.stack);
  res.status(err.status || 500);
  res.json({ error: err.message || err.toString() });
}

function api(server,prefix) {
  console.log("the prefix is "+prefix)
  server.use(prefix+'/odoo', customerApi, handleError);
  server.use(prefix+'/search', searchApi, handleError);
  server.use(prefix+'/user', userApi, handleError);
}

module.exports = api;