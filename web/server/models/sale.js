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
            let domain = [];
            if (state != null) {
                domain.push(["state", "ilike", state]);
            }
            if (invoice_status != null) {
                domain.push(["invoice_status", "ilike", invoice_status]);
            }
            result = await server.search_read(model, { domain: domain, fields: ["name", "dob", "product_name", "product_variant", "product_color", "state", "finance_type", "financier_name", "payment_date", "delivery_date", "stock_status", "balance_amount"] });
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
            let domain = [];
            domain.push(["state", "ilike", state]);
            domain.push(["picking_type_id.code", "=", 'outgoing']);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "user_id", "team_id", "state", "scheduled_date", "picking_type_code"] });
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }

    async getInventoryStock(user, { model, domain = [], fields = [] }) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            result = await server.read_group(model, { domain: domain, groupby: ["state"], fields: ["name"] }, true);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }

    async priceListItem(user, { name }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'product.pricelist.item';
            let domain = [];
            domain.push(["pricelist_id", "=", name]);
            result = await server.search_read(model, { domain: domain, fields: ["product_id", "pricelist_id.name", "fixed_price"] });
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return base.cleanModels(result.records);
    }

}

module.exports = new Sale();
