const _ = require('lodash');
const odoo = require('../odoo_server');

class Base {
    async getModel(user, { model, id }) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            id = parseInt(id);
            result = await server.get(model, id);
            return this.cleanModel(result[0]);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }
    cleanModel(model) {
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
        return model;
    }
}
module.exports = new Base();
