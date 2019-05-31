const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');
const firebase = require('../ext/firebase');

class Task{
    
    async sendTaskNotification(user,{modelName}) {
        let self = this;
        console.log("user" + '', user);
        console.log("partner" + '', user.partner_id);
        let states = ["today"];
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            console.log("user id",server.context);
            let model = 'mail.activity';
            let domain =this.getActivityDomain(states[0]);
          //  domain.push(["res_model", "=", modelName]);
            domain.push(["user_id", "=", server.uid]);
            console.log("domain ",domain);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id","user_id","res_model"]});
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        let firebase_response = await firebase(user,{
            title: 'Hello Good Morning  from DMS',
            message: 'You have '+result.records.length+' tasks scheduled today'
        });
        return {success:true,message:firebase_response};
    }
    async getUserTasks(user,{modelName}) {
        let self = this;
        console.log("user" + '', user);
        console.log("partner" + '', user.partner_id);
        let states = ["today"];
      

        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            console.log("user id",server.context);
            let model = 'mail.activity';
            let domain =this.getActivityDomain(states[0]);
          //  domain.push(["res_model", "=", modelName]);
            domain.push(["user_id", "=", server.uid]);
            console.log("domain ",domain);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id","user_id","res_model"]});
            console.log(model + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return base.cleanModels(result.records);
    }
    getActivityDomain(state) {
        let domain = [];
        var today = new Date().toISOString().slice(0, 10);
        switch (state) {
            case "planned":
                domain.push(["date_deadline", ">", today]);
                break;
            case "today":
                domain.push(["date_deadline", "=", today]);
                break;
            case "overdue":
                domain.push(["date_deadline", "<", today]);
                break;
        }
        return domain;
    };    
    async getActivities(user, { id,modelName }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let activity_model = 'mail.activity';
            console.log("Id is - ", id);
            let domain = [];
            domain.push(["res_model", "=", modelName]);
            domain.push(["res_id", "=", id]);
            result = await server.search_read(activity_model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id","user_id"] });
            console.log(modelName + '', result);
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return base.cleanModels(result.records);
    }
}


module.exports = new Task();
