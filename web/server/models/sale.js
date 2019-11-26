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
            result.records = base.cleanModels(result.records);
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
            result = await server.search_read(model, { domain: domain, fields: ["product_id", "date_start", "date_end", "fixed_price"] });
            result.records = base.cleanModels(result.records);
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }

    async getOrderCountByState(user, { state }) {
        let result = [];
        try {
            console.log("The state is ", state);
            let server = odoo.getOdoo(user.email);
            let model = 'sale.order';
            let domain = [];
            domain.push(["state", "=", state]);
            result = await server.search(model, { domain: domain }, true);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }

    /* to get quotation's price details based on lead id */
    async saleOrder(user, { leadId }) {
        let orderDetails = [];
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        domain.push(["opportunity_id", "=", parseInt(leadId)]);
        orderDetails = await server.search_read(model, { domain: domain, fields: ["name", "partner_id", "amount_untaxed", "amount_tax", "amount_total", "team_id", "opportunity_id"] });
        return orderDetails;
    }

    /* to get sale order price details based on order id */
    async saleOrderPrice(user, { orderId }) {
        let priceDetails = [];
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order.line';
        let domain = [];
        domain.push(["order_id", "=", parseInt(orderId)]);
        priceDetails = await server.search_read(model, { domain: domain, fields: ["name", "order_id", "price_unit", "price_subtotal", "price_tax", "price_total", "price_reduce", "product_id"] });
        return priceDetails;
    }

    /* to get total quotation count */
    async quotationCount(user) {
        console.log("Inside quotationCount in stock js ");
        let quotationCount = null;
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        quotationCount = await server.search(model, { domain: domain }, true);
        return quotationCount;
    }

}

module.exports = new Sale();
