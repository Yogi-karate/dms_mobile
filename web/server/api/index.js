const publicApi = require('./public');
const customerApi = require('./customer');
const utilsApi = require('./utils');
const leadsApi = require('./leads');
const salesApi = require('./sales');
const tasksApi = require('./tasks');
const dashboardApi = require('./dashboard');
const adminApi = require('./admin');
const vehicleLeadApi = require('./vehicleLeads');
//const adminApi = require('./admin');

function api(server) {
  server.use('/api/v1/public', publicApi);
  server.use('/api/v1/customer', customerApi);
  server.use('/api/v1/utils', utilsApi);
  server.use('/api/v1/leads', leadsApi);
  server.use('/api/v1/sales', salesApi);
  server.use('/api/v1/tasks', tasksApi);
  server.use('/api/v1/dashboard', dashboardApi);
  server.use('/api/v1/admin', adminApi);
  server.use('/api/v1/vehicleLeads', vehicleLeadApi);
}

module.exports = api;
