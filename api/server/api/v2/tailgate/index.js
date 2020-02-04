const publicApi = require('./public');
const userApi = require('./user');
const eventApi = require('./event');
const adminApi = require('./admin');
const locationRegistrationApi = require('./locationRegistration');

function api(server,URLPrefix) {
  server.use(URLPrefix+'/public', publicApi);
  server.use(URLPrefix+'/user', userApi);
  server.use(URLPrefix+'/event', eventApi);
  server.use(URLPrefix+'/admin', adminApi);
  server.use(URLPrefix+'/locationRegistration', locationRegistrationApi);
}

module.exports = api;
