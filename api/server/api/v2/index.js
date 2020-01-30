const commonApi = require('./common');
const dmsApi = require('./dms');
const tailgateApi = require('./tailgate');

const logger = require('../../logs');

function handleError(err, req, res, next) {
  logger.error(err.stack);
  res.status(err.status || 500);
  res.json({ error: err.message || err.toString() });
}

function api(server) {
  commonApi(server,'/api/v2/common');
  tailgateApi(server,'/api/v2/tailgate');
  dmsApi(server,'/api/v2/dms');
}

module.exports = api;