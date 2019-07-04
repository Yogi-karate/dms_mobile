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
        console.log("the result for searchModels", result)
        return result;
    }
    cleanModels(models) {
        models.forEach(model => {
            if (model != null) {
                let keys = Object.keys(model);
                keys.forEach(key => {
                    if (Array.isArray(model[key]) && model[key].length == 0) {
                        console.log("Key of array type", key, model[key]);
                        model[key] = [];
                    } else if (model[key] === false) {
                        console.log("Key of false type", key, model[key]);
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
            console.log("inside getUserRole user email is", user.email);
            let domain = [];
            domain.push(["manager_user_ids", "in", [server.uid]]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "team_type"] });
            if (result.length !=0) {
                return { role: "Manager", teams: result }
            } else {
                domain = [["user_id", "=",server.uid]];
                result = await server.search_read(model, { domain: domain, fields: ["name", "id", "team_type"] });
                if (result.length != 0) {
                    return { role: "Team_Lead", teams: result }
                }
            }
            console.log("The model and result is ", model + '', result);
            return { role: "user",teams:""}
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }
}
module.exports = new Base();
