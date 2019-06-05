const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');

class Sale {
    async getDashboardCounts(user) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'sale.order';
            let group = await server.read_group(model, { domain: [], groupby: ["state"] }, true);
            console.log(model + '', group);
            result.push({ result: group });
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }

    async searchOrderByState(user, { state, invoice_status }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'sale.order';
            console.log("State:", state);
            let domain = [];
            if (state != null) {
                domain.push(["state", "ilike", state]);
            }
            if (invoice_status != null) {
                domain.push(["invoice_status", "ilike", invoice_status]);
            }
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "user_id", "team_id", "state", "date_order", "invoice_status", "amount_total"] });
            console.log(model + '', result);
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }

    async searchInventoryByState(user, { state }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'stock.picking';
            console.log("State:", state);
            let domain = [];
            domain.push(["state", "ilike", state]);
            domain.push(["picking_type_id.code", "=", 'outgoing']);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "user_id", "team_id", "state", "scheduled_date", "picking_type_code"] });
            console.log(model + '', result);
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }
}

module.exports = new Sale();
