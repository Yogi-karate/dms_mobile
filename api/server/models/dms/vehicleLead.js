const _ = require('lodash');
const odoo = require('../core/odoo_server');
const base = require('../core/base');

class VehicleLead {
    async getDashboardCounts(user, { callType }) {
        let result = [];
        let server = odoo.getOdoo(user.email);
        let model = 'dms.vehicle.lead';
        let states = ["overdue", "today", "planned", "completed"];
        let self = this;
        for (let i = 0; i < states.length; i++) {
            let group = await server.search(model, { domain: this.getActivityDomain(states[i], callType) }, true);
            result.push({ state: states[i], result: group });
        };
        return result;
    }

    getActivityDomain(state, callType) {
        let domain = [];
        var today = new Date().toISOString().slice(0, 10);
        switch (state) {
            case "planned":
                domain.push(["current_due_date", ">", today]);
                domain.push(["opportunity_type", "ilike", callType]);
                domain.push(["type", "=", "lead"]);
                break;
            case "today":
                domain.push(["current_due_date", "=", today]);
                domain.push(["opportunity_type", "ilike", callType]);
                domain.push(["type", "=", "lead"]);
                break;
            case "overdue":
                domain.push(["current_due_date", "<", today]);
                domain.push(["opportunity_type", "ilike", callType]);
                domain.push(["type", "=", "lead"]);
                break;
            case "completed":
                domain.push(["opportunity_type", "ilike", callType]);
                domain.push(["call_state", "=", 'done']);
                domain.push(["type", "=", "lead"]);
                break;
        }
        return domain;
    };
    async searchLeadsByState(user, { state, callType, userId }) {
        let result = null;
        let server = odoo.getOdoo(user.email);
        let model = 'dms.vehicle.lead';
        let domain = [];
        if (state != null) {
            domain = this.getActivityDomain(state, callType);
        }
        if (userId != null && !isNaN(userId) && userId != '') {
            domain.push(["user_id", "=", parseInt(userId)]);
        }
        result = await server.search_read(model, { domain: domain, fields: ["name", "id", "current_due_date", "mobile", "partner_name", "user_id", "team_id", "stage_id", "call_state", "disposition"] });
        result.records.forEach(record => {
            record.activity_date_deadline = record.current_due_date;
            delete record.current_due_date;
        });
        result.records.sort(function (record1, record2) {
            var dateA = new Date(record1.activity_date_deadline), dateB = new Date(record2.activity_date_deadline)
            return dateA - dateB //sort by date ascending
        });
        result.records = base.cleanModels(result.records);
        return result;
    }
    async createActivity(user, req) {
        let result = null;
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
        result = await server.create(model, req);
        if (result == null) {
            throw new Error("Cannot create Activity");
        }
        return { "id": result, "Message": "Success" };
    }
    async getActivities(user, { id }) {
        let result = null;
        let server = odoo.getOdoo(user.email);
        let model = 'mail.activity';
        let domain = [];
        domain.push(["res_model", "=", "dms.vehicle.lead"]);
        domain.push(["res_id", "=", id]);
        result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id", "user_id", "res_model"] });
        return base.cleanModels(result.records);
    }
    async setActivities(user, { id, feedback, disposition_id }) {
        let result = null;
        let server = odoo.getOdoo(user.email);
        let model = 'mail.activity';
        result = await server.action_feedback_disposition(model, { id: id, feedback: feedback, disposition_id: disposition_id });
        return result;
    }
}

module.exports = new VehicleLead();
