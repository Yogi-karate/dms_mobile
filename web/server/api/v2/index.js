const vehicleLeadApi = require('./vehicleLeads');
const logger = require('../../logs');

function handleError(err, req, res, next) {
  logger.error(err.stack);
  res.status(err.status || 500);
  res.json({ error: err.message || err.toString() });
}

function api(server) {
  server.use('/api/v2/vehicleLeads', vehicleLeadApi, handleError);
}

module.exports = api;
