const publicApi = require('./public');
const vehicleLeadApi = require('./vehicleLeads');
const utilsApi = require('./utils');
const leadsApi = require('./leads');
const salesApi = require('./sales');
const tasksApi = require('./tasks');
const dashboardApi = require('./dashboard');
const adminApi = require('./admin');
const stockApi = require('./stock');

const logger = require('../../../logs');

function handleError(err, req, res, next) {
  logger.error(err.stack);
  res.status(err.status || 500);
  res.json({ error: err.message || err.toString() });
}

function api(server,URLPrefix) {
  server.use(URLPrefix+'/public', publicApi, handleError);
  server.use(URLPrefix+'/utils', utilsApi, handleError);
  server.use(URLPrefix+'/leads', leadsApi, handleError);
  server.use(URLPrefix+'/sales', salesApi, handleError);
  server.use(URLPrefix+'/tasks', tasksApi, handleError);
  server.use(URLPrefix+'/dashboard', dashboardApi, handleError);
  server.use(URLPrefix+'/admin', adminApi, handleError);
  server.use(URLPrefix+'/stock', stockApi, handleError);
  server.use(URLPrefix+'/vehicleLeads', vehicleLeadApi, handleError);
}

module.exports = api;