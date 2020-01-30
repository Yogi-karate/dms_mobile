const _ = require('lodash');
const odoo = require('./odoo_server');

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
            if (user.email === odoo.admin_user) {
                result = await server.search_read(model, { domain: domain, fields: ["name", "id", "team_type"] });
                let moduleType = this.checkForTeamType(result.records);
                return { role: "Admin", teams: result, module: moduleType };
            } else {
                domain.push(["manager_user_ids", "in", [server.uid]]);
                result = await server.search_read(model, { domain: domain, fields: ["name", "id", "team_type"] });
                if (result.length != 0) {
                    let moduleType = this.checkForTeamType(result.records);
                    console.log("The moduleType isss ", moduleType);
                    return { role: "Manager", teams: result, module: moduleType };
                } else {
                    domain = [["user_id", "=", server.uid]];
                    result = await server.search_read(model, { domain: domain, fields: ["name", "id", "team_type"] });
                    if (result.length != 0) {
                        let moduleType = this.checkForTeamType(result.records);
                        return { role: "Team_Lead", teams: result, module: moduleType };
                    } else {
                        domain = [["member_ids", "=", server.uid]];
                        result = await server.search_read(model, { domain: domain, fields: ["name", "id", "team_type"] });
                        if (result.length != 0) {
                            let moduleType = this.checkForTeamType(result.records);
                            return { role: "user", teams: { records: [] }, module: moduleType };
                        }
                    }
                }
            }
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }

    async getUserCompanies(user) {
        if (user != null && user != undefined) {
            let finalResult = {};
            console.log("inside getUserCompanies ", user);
            let server = odoo.getOdoo(user.email);
            console.log("the server inside getUserCompanies ", server.uid);
            let result = await server.search_read("res.users", { domain: [["id", "=", server.uid]], fields: ["company_id", "company_ids"] });
            console.log("The companies for logged in user is ", result.records[0].company_ids);
            let companies = null;
            let model = 'res.company';
            let domain = [];
            domain.push(["id", "in", result.records[0].company_ids]);
            companies = await server.search_read(model, { domain: domain, fields: ["name", "id"] });
            console.log("the companies are ", companies);
            finalResult.company_id = result.records[0].company_id;
            finalResult.company_ids = companies.records;
            console.log("the getUserCompanies final result is are ", finalResult);
            return finalResult;
        } else {
            return { "result": "user undefined" };
        }

    };

    checkForTeamType(data) {
        console.log("inside checkType ", data);
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
