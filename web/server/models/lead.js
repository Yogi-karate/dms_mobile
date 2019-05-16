const _ = require('lodash');
const odoo = require('../odoo_server');

class Lead {
    async getCounts(user) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            result = await server.search(model, { domain: [["kanban_state", "=", "red"]] }, true);
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
    async searchLeadsByState(user, { state, stage }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.lead';
            console.log("State:", state, stage);
            var today = new Date().toISOString().slice(0, 10);
            let domain = [];
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
            domain.push(["stage_id.name", "ilike", stage]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "user_id", "team_id", "activity_state", "stage_id"] });
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }
}

module.exports = new Lead();
