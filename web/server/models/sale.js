const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');

class Sale {
    async getDashboardCounts(user) {
        let result = [];
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let group = await server.read_group(model, { domain: [], groupby: ["state"] }, true);
        result.push({ result: group });
        return result;
    }

    async searchOrderByState(user, { state, invoice_status, leadId }) {
        let result = null;
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        if (state != null) {
            domain.push(["state", "ilike", state]);
        }
        if (invoice_status != null) {
            domain.push(["invoice_status", "ilike", invoice_status]);
        }
        if (leadId != null) {
            domain.push(["opportunity_id", "=", parseInt(leadId)]);
        }
        result = await server.search_read(model, { domain: domain, fields: ["name", "dob", "partner_id", "team_id", "user_id", "booking_amt", "state"] });
        result.records = base.cleanModels(result.records);
        return result;
    }

    async searchInventoryByState(user, { state }) {
        let result = null;
        let server = odoo.getOdoo(user.email);
        let model = 'stock.picking';
        let domain = [];
        domain.push(["state", "ilike", state]);
        domain.push(["picking_type_id.code", "=", 'outgoing']);
        result = await server.search_read(model, { domain: domain, fields: ["name", "id", "user_id", "team_id", "state", "scheduled_date", "picking_type_code"] });
        return result;
    }

    async getInventoryStock(user, { model, domain = [], fields = [] }) {
        let result = [];
        let server = odoo.getOdoo(user.email);
        result = await server.read_group(model, { domain: domain, groupby: ["state"], fields: ["name"] }, true);
        return result;
    }

    async priceListItem(user, { name }) {
        let result = null;
        let server = odoo.getOdoo(user.email);
        let model = 'product.pricelist.item';
        let domain = [];
        domain.push(["pricelist_id", "=", name]);
        result = await server.search_read(model, { domain: domain, fields: ["product_id", "date_start", "date_end", "fixed_price"] });
        result.records = base.cleanModels(result.records);
        return result;
    }

    async getOrderCountByState(user, { state }) {
        let result = [];
        console.log("The state is ", state);
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        domain.push(["state", "=", state]);
        result = await server.search(model, { domain: domain }, true);
        return result;
    }

    /* to get sale order price details based on order id */
    async saleOrderPrice(user, { orderId }) {
        let orderDetails = [];
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        domain.push(["id", "=", parseInt(orderId)]);
        orderDetails = await server.search_read(model, { domain: domain, fields: ["amount_untaxed", "amount_tax", "amount_total"] });
        if (orderDetails != null && orderDetails != undefined && orderDetails.records.length > 0) {
            let priceDetails = [];
            let model1 = 'sale.order.line';
            let domain1 = [];
            domain1.push(["order_id", "=", parseInt(orderId)]);
            priceDetails = await server.search_read(model1, { domain: domain1, fields: ["name", "price_total"] });
            priceDetails.saleorder = orderDetails.records[0];
            return priceDetails;
        } else {
            return { length: 0, records: [] };
        }
    }

    /* get the customer details for sale order */
    async saleOrderCustomer(user, { orderId }) {
        let orderDetails = [];
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        domain.push(["id", "=", parseInt(orderId)]);
        orderDetails = await server.search_read(model, { domain: domain, fields: ["partner_id", "product_name", "product_variant", "product_color", "date_order", "delivery_date", "warehouse_id"] });
        if (orderDetails != null && orderDetails != undefined && orderDetails.records.length > 0) {
            orderDetails.records = base.cleanModels(orderDetails.records);
            return orderDetails;
        } else {
            return { length: 0, records: [] };
        }
    }

    /* to get total quotation count */
    async quotationCount(user) {
        console.log("Inside quotationCount in stock js ");
        let quotationCount = null;
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        domain.push(["state", "=", "draft"]);
        quotationCount = await server.search(model, { domain: domain }, true);
        return quotationCount;
    }

    /* confirm quotations */
    async confirmQuotation(user, { orderId }) {
        let actionResult = null;
        console.log("Inside confirmQuotation in stock js ");
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        if (orderId != null) {
            actionResult = await server.action_confirm(model, { id: parseInt(orderId) });
            console.log("the action_confirm result is ", actionResult);
        }
        return actionResult;
    }

    /* get the payment details for sale order */
    async saleOrderPaymentDetails(user, { orderId }) {
        let orderDetails = [];
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        domain.push(["id", "=", parseInt(orderId)]);
        orderDetails = await server.search_read(model, { domain: domain, fields: ["invoice_ids"] });
        if (orderDetails != null && orderDetails != undefined && orderDetails.records.length > 0) {
            let invoiceIds = orderDetails.records[0].invoice_ids;
            console.log("The invoice ids are ", invoiceIds);
            let paymentDetails = [];
            let model1 = 'account.payment';
            let domain1 = [];
            domain1.push(["invoice_ids", "in", invoiceIds]);
            paymentDetails = await server.search_read(model1, { domain: domain1, fields: ["payment_type", "communication", "payment_date", "amount"] });
            if (paymentDetails.records.length > 0) {
                paymentDetails.records = base.cleanModels(paymentDetails.records);
                return paymentDetails;
            }
            else {
                console.log("payment details records are empty");
                return { length: 0, records: [] };
            }
        } else {
            console.log("order details records are empty");
            return { length: 0, records: [] };
        }
    }

    /* give order details to display for updation */
    async updateOrderBookingDetails(user, { orderId }) {
        let orderDetails = [];
        let server = odoo.getOdoo(user.email);
        let model = 'sale.order';
        let domain = [];
        domain.push(["id", "=", parseInt(orderId)]);
        orderDetails = await server.search_read(model, { domain: domain, fields: ["finance_type", "finance_payment_date", "financier_name", "margin_payment_date", "delivery_date", "booking_amt", "dob", "priority", "finance_pmt", "margin_pmt", "balance_amount", "stock_status"] });
        if (orderDetails != null && orderDetails != undefined && orderDetails.records.length > 0) {
            orderDetails.records = base.cleanModels(orderDetails.records);
            return orderDetails.records[0];
        } else {
            console.log("order details records are empty");
            return {};
        }
    }

}

module.exports = new Sale();
