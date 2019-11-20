const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');

class Stock {
    async vehicleList(user) {
        console.log("Inside vehicleList in stock js ", user);
        let vehicles = null;
        let server = odoo.getOdoo(user.email);
        let model = 'vehicle';
        let domain = [];
        domain.push(["state", "!=", "sold"]);
        vehicles = await server.search_read(model, { domain: domain, fields: ["model", "color", "variant", "name", "location_id", "state", "age", "allocation_state","partner_id"] });
        vehicles.records = base.cleanModels(vehicles.records);
        return vehicles;
    }
}

module.exports = new Stock();
