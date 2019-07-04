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
    async searchModels(user, { model, domain = [], fields = [] }) {
        let server = odoo.getOdoo(user.email);
        console.log("Hello from base",fields,domain);
        let result = await server.search(model, domain, true );
        console.log("RESULT", result)
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
}
module.exports = new Base();
