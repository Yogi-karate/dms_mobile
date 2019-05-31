const publicApi = require('./public');
const customerApi = require('./customer');
const utilsApi = require('./utils');
const leadsApi = require('./leads');
const salesApi = require('./sales');

//const adminApi = require('./admin');

function api(server) {
  server.use('/api/v1/public', publicApi);
  server.use('/api/v1/customer', customerApi);
  server.use('/api/v1/utils', utilsApi);
  server.use('/api/v1/leads', leadsApi);
  server.use('/api/v1/sales', salesApi);
}

module.exports = api;
