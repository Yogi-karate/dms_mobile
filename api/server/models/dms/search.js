const _ = require('lodash');
const odoo = require('../../odoo_server');
const base = require('./base');

class Search {
    static getDomainFromParams(params) {
        let domain = []
        if (params['q']) {
            Object.keys(params.q).forEach(param => {
                console.log("The param is", param);
                console.log("the param value is ", params.q[param]);
            });
        }
        return domain;
    }
    async find(user, params) {
        let result = [];
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = Search.getDomainFromParams(params);
        result = await server.search_read(model, { domain: domain, fields: ["name", "dob", "user_id", "booking_amt"] });
        result.records = base.cleanModels(result.records);
        return result;
    }
}

module.exports = new Search();