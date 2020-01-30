const customerApi = require('./customer');

const logger = require('../../../logs');

function handleError(err, req, res, next) {
  logger.error(err.stack);
  res.status(err.status || 500);
  res.json({ error: err.message || err.toString() });
}

function api(server,prefix) {
  console.log("the prefix is "+prefix)
  server.use(prefix+'/common', customerApi, handleError);
}

module.exports = api;