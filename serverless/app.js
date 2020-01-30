const notificationApi = require('./notification');
const serviceLeadSmsApi = require('./serviceLeadSms');
const insuranceLeadSmsApi = require('./insuranceLeadSms');
const insuranceBookingSmsApi = require('./insuranceBookingSms');
const serviceBookingSmsApi = require('./serviceBookingSms');

notificationApi();
serviceLeadSmsApi();
insuranceLeadSmsApi();
serviceBookingSmsApi();
insuranceBookingSmsApi();