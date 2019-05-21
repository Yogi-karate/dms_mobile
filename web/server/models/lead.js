const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');

class Lead {
    async getDashboardCounts(user) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            let states = ["overdue","today","planned"];
            let self = this;
            for (let i = 0; i < states.length; i++) {
                console.log("state is :", states[i]);
                let group = await server.read_group(model, { domain: this.getActivityDomain(states[i]), groupby: ["stage_id"] }, true);
                console.log(model + '', group);
                result.push({state:states[i],result:group});
            };
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }

    }
    async getEnquiry(user,{id}) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'dms.enquiry';
            console.log("Hello from get enquiry");
            let enq_id = await server.search(model, { domain: [["opportunity_ids", "in", [id]]]});
            if (!enq_id) throw new Error('Enquiry Id for this opportunity is not present'); 
            result = await base.getModel(user,{model:model,id:enq_id});
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
    getActivityDomain(state){
        let domain = [];
        var today = new Date().toISOString().slice(0, 10);
        switch (state) {
            case "planned":
                domain.push(["activity_date_deadline", ">", today]);
                break;
            case "today":
                domain.push(["activity_date_deadline", "=", today]);
                break;
            case "overdue":
                domain.push(["activity_date_deadline", "<", today]);
                break;
        }
        return domain;
    };
    async searchLeadsByState(user, { state, stage }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            console.log("State:", state, stage);
            let domain = this.getActivityDomain(state);
            domain.push(["stage_id.name", "ilike", stage]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "user_id", "team_id", "stage_id"] });
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
    async getActivities(user, { id }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'mail.activity';
            console.log("Id is - ", id);
            let domain = [];
            domain.push(["res_model", "=", "crm.lead"]);
            domain.push(["res_id", "=", id]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id"] });
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return base.cleanModel(result.records[0]);
    }
    async setActivities(user, { id,feedback }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'mail.activity';
            console.log("Feedback is  - ", feedback);
            result = await server.action_feedback(model, { id: id, feedback:feedback});
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
}

module.exports = new Lead();
