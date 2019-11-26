const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');

class Lead {
    async getDashboardCounts(user) {
        let result = [];
        let server = odoo.getOdoo(user.email);
        let model = 'crm.lead';
        let states = ["overdue", "today", "planned", "completed"];
        let self = this;
        for (let i = 0; i < states.length; i++) {
            let group = await server.read_group(model, { domain: this.getActivityDomain(states[i]), groupby: ["stage_id"] }, true);
            result.push({ state: states[i], result: group });
        };
        return result;
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
            case "completed":
                domain.push(["activity_ids", "=", false]);
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
    async searchLeadsByState(user, { state, stage, userId }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            let domain = [];
            if (state != null) {
                domain = this.getActivityDomain(state);
            }
            if (stage != null || stage != undefined) {
                domain.push(["stage_id.name", "ilike", stage]);
            }
            if (userId != null && !isNaN(userId) && userId != '') {
                domain.push(["user_id", "=", parseInt(userId)]);
            }
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "activity_date_deadline", "mobile", "partner_name", "user_id", "team_id", "stage_id", "email_from", "sale_number"] });
            result.records.sort(function (record1, record2) {
                var dateA = new Date(record1.activity_date_deadline), dateB = new Date(record2.activity_date_deadline)
                return dateA - dateB //sort by date ascending
            });
            result.records = base.cleanModels(result.records);
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
            domain.push(["res_model", "=", "crm.lead"]);
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
            var lastDay = new Date(current_year, current_month + 1, 0);
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
        console.log(today.getMonth(), today.getFullYear());
        return this.getLeadDashboards(user, { id: id }, { month: today.getMonth() }, { year: today.getFullYear() });
    }
    async getDailyLeads(user, { id }) {
        var today = new Date();
        return this.getDailyLeadsNew(user, { team: null }, { id: id }, { month: today.getMonth() }, { year: today.getFullYear() });
    }
    async getDailyLeadsNew(user, { team }, { id }, { month }, { year }) {
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
            var lastDay = new Date(current_year, current_month + 1, 0);
            domain.push(["user_id", "=", parseInt(id)]);
            if (team) {
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

    async lostReasons(user, { type }) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lost.reason';
            let domain = [];
            let self = this;
            switch (type) {
                case 'Service':
                    domain.push(["type", "=", "Service"]);
                    break;
                case 'Insurance':
                    domain.push(["type", "=", "Insurance"]);
                    break;
                case 'Sales':
                    domain.push(["type", "=", "Vehicle"]);
                    break;
                default:
                    break;
            }
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "type"] });
            if (result == null) {
                return [];
            } else {
                return result;
            }
        } catch (err) {
            return { error: err.message || err.toString() };
        }

    }

    async createQuotation(user, req) {
        let newQuotation = null;
        let actionResult = null;
        let server = odoo.getOdoo(user.email);
        let model = 'dms.enquiry2sale.order';
        newQuotation = await server.create(model, req);
        console.log("The new quotation created id ", newQuotation);
        if (newQuotation != null) {
            actionResult = await server.action_apply(model, { id: newQuotation });
            console.log("the action_apply result is ", actionResult);
        }
        return actionResult;
    }

    /* get lead details which contains enquiry data */
    async leadDetails(user, { leadId }) {
        let leadDetails = [];
        let server = odoo.getOdoo(user.email);
        let model = 'crm.lead';
        let domain = [];
        domain.push(["id", "=", parseInt(leadId)])
        let fields = ["name", "team_id", "partner_name", "mobile", "enquiry_id", "user_id", "sale_number", "stage_id"];
        leadDetails = await server.search_read(model, { domain: domain, fields: fields });
        console.log("The lead details areeeeee ", leadDetails);
        if (leadDetails == null || leadDetails == undefined || leadDetails.records.length < 1) {
            return { length: 0, records: [] };
        } else {
            if (leadDetails.records[0].enquiry_id != false) {
                let enquiryDetails = [];
                let enquiryId = leadDetails.records[0].enquiry_id[0];
                let model1 = 'dms.enquiry';
                let domain1 = [];
                domain1.push(["id", "=", parseInt(enquiryId)]);
                let fields1 = ["product_id", "product_color", "product_variant", "source_id"];
                enquiryDetails = await server.search_read(model1, { domain: domain1, fields: fields1 });
                const finalEnquiry = enquiryDetails.records.map(enquiry => {
                    return enquiry;
                })
                leadDetails.records[0].enquiry = finalEnquiry[0];
            } else {
                leadDetails.records[0].enquiry = {
                    product_id: "",
                    product_color: "",
                    product_variant: "",
                    id: "",
                    source_id: ""
                }
            }
            leadDetails.records = base.cleanModels(leadDetails.records);
            return leadDetails;
        }
    }

}

module.exports = new Lead();