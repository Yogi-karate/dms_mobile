const _ = require('lodash');
const odoo = require('../odoo_server');

class Base {
    async getModel(user, { model, id }) {
        let result = [];
        try {
            let server = odoo.getOdoo(user.email);
            id = parseInt(id);
            result = await server.get(model, id);
            let model_new = result[0];
            if (model_new != null) {
                let keys = Object.keys(model_new);
                keys.forEach(key => {
                    if (Array.isArray(model_new[key]) && model_new[key].length == 0) {
                        console.log("Key of array type", key, model_new[key]);
                        model_new[key] = [];
                    } else if (model_new[key] === false) {
                        model_new[key] = "";
                    }
                });
                return model_new;
            }
            return null;
        } catch(err) {
        return { error: err.message || err.toString() };
        }
    }
}

module.exports = new Base();
