const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');

class Lead {
    async getDashboardCounts(user) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            let states = ["overdue", "today", "planned"];
            let self = this;
            for (let i = 0; i < states.length; i++) {
                console.log("state is :", states[i]);
                let group = await server.read_group(model, { domain: this.getActivityDomain(states[i]), groupby: ["stage_id"] }, true);
                console.log(model + '', group);
                result.push({ state: states[i], result: group });
            };
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }

    }
    async getStageCounts(user) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            let states = ["overdue", "today", "planned"];
            let domain = [];
            let fields = ["stage_id"];
            let self = this;
            let group = await server.read_group(model, { domain: domain, groupby: ["stage_id"], fields: fields }, true);
            console.log("The getStageCountssssssss", group);
            if (group == null) {
                return [];
            } else {
                return group;
            }
        } catch (err) {
            return { error: err.message || err.toString() };
        }

    }
    async getEnquiry(user, { id }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'dms.enquiry';
            console.log("Hello from get enquiry");
            let enq_id = await server.search(model, { domain: [["opportunity_ids", "in", [id]]] });
            if (!enq_id) throw new Error('Enquiry Id for this opportunity is not present');
            result = await base.getModel(user, { model: model, id: enq_id });
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
    getActivityDomain(state) {
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
    async search(user, domain) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            console.log("The domain is ", domain);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "user_id", "team_id", "stage_id"] });
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
    async searchLeadsByState(user, { state, stage, create_date }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            console.log("State:", state, stage);
            let domain = [];
            if (create_date != null) {
                domain.push(["create_date", ">=", create_date]);
            }
            if (state != null) {
                domain = this.getActivityDomain(state);
            }
            if (stage != null || stage != undefined) {
                domain.push(["stage_id.name", "ilike", stage]);
            }
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "user_id", "team_id", "stage_id"] });
            console.log(model + '', result);
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
            domain.push(["model", "=", "crm.lead"]);
            let ir_models = await server.search_read(ir_model, { domain: domain, fields: ["name", "model", "id"] });
            console.log(ir_model + '', ir_models);
            if (ir_models.records.length > 0) {
                req['res_model_id'] = ir_models.records[0].id;
                console.log("Added model id to create json", req);
            } else {
                throw new Error("Cannot create Activity");
            }
            let result = await server.create(model, req);
            console.log(model, result);
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
            console.log("Id is - ", id);
            let domain = [];
            domain.push(["res_model", "=", "crm.lead"]);
            domain.push(["res_id", "=", id]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id", "user_id", "res_model"] });
            console.log(model + '', result);
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
            console.log("Feedback is  - ", feedback);
            result = await server.action_feedback(model, { id: id, feedback: feedback });
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
    async getLeadDashboards(user, { id },{month},{year}) {
        let result = [];
        try {
        let server = odoo.getOdoo(user.email);
        let model = 'crm.lead';
        let domain = [];
        let domain1 = [];
        let current_year = parseInt(year);
        let current_month = parseInt(month);
        console.log("The current month and year ",current_month,current_year);
        let fields = ["user_id", "user_id_count", "user_booked_id"];
        var today = new Date();
        var firstDay = new Date(current_year,current_month-1, 1);
        var lastDay = new Date(current_year,current_month-1, 0);
        console.log("The current month first day is ", firstDay.toLocaleDateString());
        console.log("The current month last day is ", lastDay.toLocaleDateString());
        domain.push(["team_id", "=", parseInt(id)]);
        domain.push(["create_date", ">", lastDay]);
        //domain1.push(["stage_id.name", "ilike","booked"]);
        domain1.push(["team_id", "=", parseInt(id)]);
        domain1.push(["stage_id", "=", 4]);
        domain1.push(["create_date", ">", lastDay]);
        let self = this;
        let group = await server.read_group(model, { domain: domain, groupby: ["user_id"], fields: fields }, true);
        let group1 = await server.read_group(model, { domain: domain1, groupby: ["user_id"] }, true);

        console.log("The group is ", group);
        console.log("The group1 is ", group1);
        console.log("The group is ", group.length);
        console.log("The group1 is ", group1.length);

        for (let i = 0; i < group.length; i++) {
            group[i].user_booked_id = 0;
            for (let j = 0; j < group1.length; j++) {
                console.log("inside for group is ", group[i].user_id[0] == group1[j].user_id[0]);
                if (group[i].user_id[0] == group1[j].user_id[0]) {
                    group[i].user_booked_id = group1[j].user_id_count;
                }
            }
        }
        result.push({ result: group });
        return result;
    } catch (err) {
        return { error: err.message || err.toString() };
    }
    }
    async getLeadDashboard(user, { id }) {
        return this.getLeadDashboards(user,id,8,2019);
    }
    async getDailyLeads(user, { id }) {
        let result = {};
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            let domain = [];
            let fields = ["user_id", "create_date"];
            var today = new Date();
            var firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
            var lastDay = new Date(today.getFullYear(), today.getMonth(), 0);
            console.log("The current month first day is ", firstDay.toLocaleDateString());
            console.log("The current month last day is ", lastDay.toLocaleDateString());
            domain.push(["user_id", "=", parseInt(id)]);
            domain.push(["create_date", ">=", firstDay])
            let self = this;
            let group = await server.search_read(model, { domain: domain, fields: fields });
            console.log("The group is ", group);
            let day = firstDay.toISOString().slice(0, 10);
            let int_date = firstDay.getDate();
            console.log(" Day is ", day, int_date, lastDay.getDate());
            let month = firstDay.getMonth() + 1;
            month = month < 10 ? "0" + month : month + "";
            while (int_date <= today.getDate()) {
                let day = int_date < 10 ? "0" + int_date : int_date;
                result[firstDay.getFullYear() + "-" + month + "-" + day] = 0;
                int_date++;
            }
            console.log("result", result);
            group.records.forEach(lead => {
                let day = lead.create_date.slice(0, 10);
                console.log("The day is ", lead.create_date.slice(0, 10));
                console.log("The result[day] is ", result[day]);
                if (result[day] >= 0) {
                    result[day]++;
                }
            })
            console.log("result", result);
            let getDailyLeadsResult = []
            Object.keys(result).forEach(key => {
                getDailyLeadsResult.push({ date: key, count: result[key] });
            })
            return getDailyLeadsResult;
        } catch (err) {
            return { error: err.message || err.toString() };
        }

    }

    async getDailyBookedLeads(user, { id }) {
        let result = {};
        try {
            var today = new Date();
            var lastDay = new Date(today.getFullYear(), today.getMonth(), 0);
            console.log("The current month last day is ", lastDay.toLocaleDateString());
            console.log("The user id is ", parseInt(id));
            return this.search(user, ['&', ["user_id", "=", parseInt(id)], ["stage_id.name", "ilike", "booked"], ["create_date", ">", lastDay]]);
        } catch (err) {
            return { error: err.message || err.toString() };
        }

    }

    async getPaymentAccount(user) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'account.payment';
            let domain = [];
            let fields = ["name", "state", "payment_type", "amount", "payment_date", "create_date", "partner_id"];
            result = await server.search_read(model, { domain: domain, fields: fields });
            console.log("The getPaymentAccount result ", result);
            if (result == null || result == undefined) {
                return { length: 0, records: [] };
            } else {
                const totalPaymentAmount = result.records.reduce(function (acc, record) {
                    console.log("The record is ", record.amount);
                    return record.amount + acc;
                }, 0);//add and get all customer payment amount. 
                console.log("The total amount is ", totalPaymentAmount);
                result.totalAmount = totalPaymentAmount;
                return result;
            }
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }
}

module.exports = new Lead();
