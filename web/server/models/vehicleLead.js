const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');

class VehicleLead {
    async getDashboardCounts(user) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'dms.vehicle.lead';
            let states = ["overdue", "today", "planned"];
            let self = this;
            for (let i = 0; i < states.length; i++) {
                let group = await server.search(model, { domain: this.getActivityDomain(states[i]) }, true);
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
            let enq_id = await server.search(model, { domain: [["opportunity_ids", "in", [id]]] });
            if (!enq_id) throw new Error('Enquiry Id for this opportunity is not present');
            result = await base.getModel(user, { model: model, id: enq_id });
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
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "user_id", "team_id", "stage_id"] });
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
    async searchLeadsByState(user, { state, create_date }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'dms.vehicle.lead';
            let domain = [];
            if (create_date != null) {
                domain.push(["create_date", ">=", create_date]);
            }
            if (state != null) {
                domain = this.getActivityDomain(state);
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
            console.log("The model and reqqqqqq ",model , req);
            let result = await server.create(model, req);
            console.log("The resultttttttttt is ", result);
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
    async getLeadDashboards(user, { id }, { month }, { year }) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            let domain = [];
            let domain1 = [];
            let current_year = parseInt(year);
            let current_month = parseInt(month);
            let fields = ["user_id", "user_id_count", "user_booked_id"];
            var firstDay = new Date(current_year, current_month, 1);
            var lastDay = new Date(current_year, current_month+1, 0);
            domain.push(["team_id", "=", parseInt(id)]);
            domain.push(["create_date", ">", firstDay]);
            domain.push(["create_date", "<=", lastDay]);
            console.log(domain);
            //domain1.push(["stage_id.name", "ilike","booked"]);
            domain1.push(["team_id", "=", parseInt(id)]);
            domain1.push(["stage_id", "=", 4]);
            domain1.push(["create_date", ">", firstDay]);
            domain1.push(["create_date", "<=", lastDay]);
            let group = await server.read_group(model, { domain: domain, groupby: ["user_id"], fields: fields }, true);
            let group1 = await server.read_group(model, { domain: domain1, groupby: ["user_id"] }, true);

            for (let i = 0; i < group.length; i++) {
                group[i].user_booked_id = 0;
                for (let j = 0; j < group1.length; j++) {
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
        var today = new Date();
        console.log(today.getMonth(),today.getFullYear());
        return this.getLeadDashboards(user, {id:id}, {month:today.getMonth()}, {year:today.getFullYear()});
    }
    async getDailyLeads(user, { id }) {
        var today = new Date();
        return this.getDailyLeadsNew(user,{team:null}, {id:id}, {month:today.getMonth()}, {year:today.getFullYear()});
    }
    async getDailyLeadsNew(user,{team}, { id }, { month }, { year }) {
        let result = {};
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            let domain = [];
            let fields = ["user_id", "create_date"];
            var today = new Date();

            let current_year = parseInt(year);
            let current_month = parseInt(month);
            var firstDay = new Date(current_year, current_month, 1);
            var lastDay = new Date(current_year, current_month+1, 0);
            domain.push(["user_id", "=", parseInt(id)]);
            if(team){
            domain.push(["team_id", "=", parseInt(team)]);
            }
            domain.push(["create_date", ">", firstDay])
            domain.push(["create_date", "<=", lastDay])
            let self = this;
            let group = await server.search_read(model, { domain: domain, fields: fields });
            let day = firstDay.toISOString().slice(0, 10);
            let int_date = firstDay.getDate();
            let cmonth = firstDay.getMonth() + 1;
            cmonth = cmonth < 10 ? "0" + cmonth : cmonth + "";
            while (int_date <= lastDay.getDate()) {
                let day = int_date < 10 ? "0" + int_date : int_date;
                result[firstDay.getFullYear() + "-" + cmonth + "-" + day] = 0;
                int_date++;
            }
            group.records.forEach(lead => {
                let day = lead.create_date.slice(0, 10);
                if (result[day] >= 0) {
                    result[day]++;
                }
            })
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
            if (result == null || result == undefined) {
                return { length: 0, records: [] };
            } else {
                const totalPaymentAmount = result.records.reduce(function (acc, record) {
                    return record.amount + acc;
                }, 0);
                //add and get all customer payment amount. 
                result.totalAmount = totalPaymentAmount;
                return result;
            }
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }
}

module.exports = new VehicleLead();
