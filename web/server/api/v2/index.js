const vehicleLeadApi = require('./vehicleLeads');
const publicApi = require('./public');
const customerApi = require('./customer');
const utilsApi = require('./utils');
const leadsApi = require('./leads');
const salesApi = require('./sales');
const tasksApi = require('./tasks');
const dashboardApi = require('./dashboard');
const adminApi = require('./admin');
const stockApi = require('./stock');

const logger = require('../../logs');

function handleError(err, req, res, next) {
  logger.error(err.stack, req.url);
  res.status(err.status || 500);
  res.json({ error: err.message || err.toString() });
}

function api(server) {
  server.use('/api/v2/public', publicApi, handleError);
  server.use('/api/v2/customer', customerApi, handleError);
  server.use('/api/v2/utils', utilsApi, handleError);
  server.use('/api/v2/leads', leadsApi, handleError);
  server.use('/api/v2/sales', salesApi, handleError);
  server.use('/api/v2/tasks', tasksApi, handleError);
  server.use('/api/v2/dashboard', dashboardApi, handleError);
  server.use('/api/v2/admin', adminApi, handleError);
  server.use('/api/v2/stock', stockApi, handleError);
  server.use('/api/v2/vehicleLeads', vehicleLeadApi, handleError);
}

module.exports = api;