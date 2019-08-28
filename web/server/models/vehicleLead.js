const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');

class VehicleLead {
    async getDashboardCounts(user, { callType }) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'dms.vehicle.lead';
            let states = ["overdue", "today", "planned"];
            let self = this;
            for (let i = 0; i < states.length; i++) {
                let group = await server.search(model, { domain: this.getActivityDomain(states[i], callType) }, true);
                result.push({ state: states[i], result: group });
            };
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }

    }
    async serviceBookingCount(user, { callType }) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = '';
            if (callType === 'Service') {
                model = 'service.booking';
            } else {
                model = 'insurance.booking';
            }
            let self = this;
            let count = await server.search(model, { domain: [] }, true);
            result.push({ result: count });
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }

    }
    async serviceBookingDetails(user, { callType }) {
        let bookingDetails = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = '';
            if (callType === 'Service') {
                model = 'service.booking';
                let self = this;
                bookingDetails = await server.search_read(model, { domain: [], fields: ["mobile", "partner_name", "booking_type", "dop", "vehicle_model", "location_id", "service_type", "user_id"], sort: "id desc" }); 
                bookingDetails.records = base.cleanModels(bookingDetails.records);
            } else {
                model = 'insurance.booking';
                let self = this;
                bookingDetails = await server.search_read(model, { domain: [], fields: ["mobile", "partner_name", "booking_type", "idv", "previous_insurance_company", "policy_no","cur_final_premium","cur_ncb","cur_dip_or_comp","pick_up_address","rollover_company"], sort: "id desc" });    
                bookingDetails.records = base.cleanModels(bookingDetails.records);
            } 
            return { result: bookingDetails };
        } catch (err) {
            return { error: err.message || err.toString() };
        }

    }

    getActivityDomain(state, callType) {
        let domain = [];
        var today = new Date().toISOString().slice(0, 10);
        switch (state) {
            case "planned":
                domain.push(["activity_date_deadline", ">", today]);
                domain.push(["call_type", "ilike", callType]);
                break;
            case "today":
                domain.push(["activity_date_deadline", "=", today]);
                domain.push(["call_type", "ilike", callType]);
                break;
            case "overdue":
                domain.push(["activity_date_deadline", "<", today]);
                domain.push(["call_type", "ilike", callType]);
                break;
        }
        return domain;
    };
    async searchLeadsByState(user, { state, create_date, callType }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'dms.vehicle.lead';
            let domain = [];
            if (create_date != null) {
                domain.push(["create_date", ">=", create_date]);
            }
            if (state != null) {
                domain = this.getActivityDomain(state, callType);
            }
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "user_id", "team_id", "stage_id"] });
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
    async createActivity(user, req) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let ir_model = 'ir.model';
            let model = 'mail.activity';
            let domain = [];
            domain.push(["model", "=", "dms.vehicle.lead"]);
            let ir_models = await server.search_read(ir_model, { domain: domain, fields: ["name", "model", "id"] });
            if (ir_models.records.length > 0) {
                req['res_model_id'] = ir_models.records[0].id;
            } else {
                throw new Error("Cannot create Activity");
            }
            let result = await server.create(model, req);
            if (result == null) {
                throw new Error("Cannot create Activity");
            }
            return { "id": result, "Message": "Success" };
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }
    async getActivities(user, { id }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'mail.activity';
            let domain = [];
            domain.push(["res_model", "=", "dms.vehicle.lead"]);
            domain.push(["res_id", "=", id]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id", "user_id", "res_model"] });
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return base.cleanModels(result.records);
    }
    async setActivities(user, { id, feedback }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'mail.activity';
            result = await server.action_feedback(model, { id: id, feedback: feedback });
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
}

module.exports = new VehicleLead();
