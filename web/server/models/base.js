const _ = require('lodash');
const odoo = require('../odoo_server');

class Base {
    async getModel(user, { model, id }) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            id = parseInt(id);
            result = await server.get(model, id);
            return this.cleanModels(result)[0];
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }
    async searchModels(user, { model, domain = [] }) {
        let server = odoo.getOdoo(user.email);
        let result = await server.search(model, { domain: domain }, true);
        return result;
    }
    cleanModels(models) {
        models.forEach(model => {
            if (model != null) {
                let keys = Object.keys(model);
                keys.forEach(key => {
                    if (Array.isArray(model[key]) && model[key].length == 0) {
                        model[key] = [];
                    } else if (model[key] === false) {
                        model[key] = "";
                    }
                });
            }
        });
        return models;
    }
    async getUserRole(user) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'crm.team';
            let domain = [];
            domain.push(["manager_user_ids", "in", [server.uid]]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "team_type"] });
            if (result.length != 0) {
                let moduleType = this.checkForTeamType(result.records);
                console.log("The moduleType isss ",moduleType);
                return { role: "Manager", teams: result , module: moduleType};
            } else {
                domain = [["user_id", "=", server.uid]];
                result = await server.search_read(model, { domain: domain, fields: ["name", "id", "team_type"] });
                if (result.length != 0) {
                    let moduleType = this.checkForTeamType(result.records);
                    return { role: "Team_Lead", teams: result , module: moduleType};
                } else {
                    domain = [["member_ids", "=", server.uid]];
                    result = await server.search_read(model, { domain: domain, fields: ["name", "id", "team_type"] });
                    if (result.length != 0) {
                        let moduleType = this.checkForTeamType(result.records);
                        return { role: "user", teams: { records: [] }, module: moduleType};
                    }
                }
            }
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }

    checkForTeamType(data) {
        console.log("inside checkType ",data);
        let modules = [];
        data.forEach(record => {
            if (!(modules.includes(record.team_type))) {
                modules.push(record.team_type);
            }
        });
        return modules;
    };
}
module.exports = new Base();
