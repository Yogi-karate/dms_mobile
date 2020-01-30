const _ = require('lodash');
const odoo = require('../core/odoo_server');
const base = require('../core/base');

class Stock {
    async vehicleList(user) {
        console.log("Inside vehicleList in stock js ", user);
        let vehicles = null;
        let server = odoo.getOdoo(user.email);
        let model = 'vehicle';
        let domain = [];
        domain.push(["state", "!=", "sold"]);
        vehicles = await server.search_read(model, { domain: domain, fields: ["model", "color", "variant", "name", "location_id", "state", "age", "allocation_state", "partner_name", "partner_mobile"] });
        if (vehicles.records != null && vehicles.records.length > 0) {
            vehicles.records = base.cleanModels(vehicles.records);
        }
        return vehicles;
    }

    async vehicleCount(user) {
        console.log("Inside vehicleList in stock js ", user);
        let vehicleCount = null;
        let server = odoo.getOdoo(user.email);
        let model = 'vehicle';
        let domain = [];
        domain.push(["state", "!=", "sold"]);
        vehicleCount = await server.search(model, { domain: domain }, true);
        return vehicleCount;
    }
}

module.exports = new Stock();
