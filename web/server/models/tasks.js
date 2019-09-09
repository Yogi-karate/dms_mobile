const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('./base');
const firebase = require('../ext/firebase');
const lead = require('../models/lead');
const MsgLog = require('../models/MsgLog');
const JobLog = require('../models/JobLog');
const MsgTemplate = require('../models/MsgTemplate');
const sms = require('../ext/sms_new');

class Task {

    async sendTaskNotification(admin, user) {
        let self = this;
        let states = ["today"];
        let result = null;
        try {

            let server = odoo.getOdoo(admin.email);
            let user_id = await server.search('res.users', { domain: [["login", "=", user.email]] });
            let model = 'mail.activity';
            let domain = this.getActivityDomain(states[0]);
            //  domain.push(["res_model", "=", modelName]);
            domain.push(["user_id", "=", user_id]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id", "user_id", "res_model"] });
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        let firebase_response = await firebase(user, {
            title: 'Hello Good Morning  from DMS',
            message: 'You have ' + result.records.length + ' tasks scheduled today'
        });
        return { success: true, message: firebase_response };
    }
    async getUserTasks(user, { modelName }) {
        let self = this;
        let states = ["today"];
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            console.log("user id", server.context);
            let model = 'mail.activity';
            let domain = this.getActivityDomain(states[0]);
            domain.push(["user_id", "=", server.uid]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id", "user_id", "res_model"] });
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
    async getActivities(user, { id, modelName }) {
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let activity_model = 'mail.activity';
            let domain = [];
            domain.push(["res_model", "=", modelName]);
            domain.push(["res_id", "=", id]);
            result = await server.search_read(activity_model, { domain: domain, fields: ["name", "id", "date_deadline", "summary", "note", "activity_type_id", "user_id"] });
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return base.cleanModels(result.records);
    }

    async sendBookingSms(user, { callType }) {
        try {
            let message = "";
            let successSmsCount = 0;
            let failedSmsCount = 0;
            let smsCount = 0;
            let failedContacts = [];
            let result = await lead.getBookingDetails(user, callType);
            if (result.records !== null) {
                result.records.forEach(async function (record) {
                    smsCount++;
                    let mobile = record.mobile;
                    let templateVars = templateType(result.smsType, record);
                    for (var prop in templateVars) {
                        if (templateVars[prop] === undefined) {
                            templateVars[prop] = '';
                        }
                    }
                    if (mobile === '9885008580' || mobile === '9701688777') {
                        mobile = '7795659269';
                        console.log("The mobile sendBookingSms ", mobile);
                        let messageLog = await MsgTemplate.list(result.smsType);
                        let templateString = messageLog[0].value;
                        let messageTemplate = function (templateString, templateVars) {
                            return new Function("return `" + templateString + "`;").call(templateVars);
                        }
                        message = messageTemplate(templateString, templateVars);
                        console.log("The message is ", message);
                        let messageResponse = await sms(mobile, message);
                        console.log("The messageLogs are ", messageResponse);
                        if (messageResponse.status === 'success') {
                            successSmsCount++;
                        } else {
                            failedSmsCount++;
                            failedContacts.push(record.mobile);
                        }
                        let NewMsgLog = { name: record.partner_name, mobile: record.mobile, sms_type: result.smsType, message: message, response: messageResponse }
                        let newMsgLogs = await MsgLog.add(NewMsgLog);
                        console.log("The newMsgLogs ", newMsgLogs);
                        if (smsCount === result.length) {
                            console.log("The countttt ", smsCount, result.length);
                            if (failedSmsCount === smsCount) {
                                let NewJobLog = { successSmsCount: successSmsCount, failedSmsCount: failedSmsCount, status: 'failed', job_name: result.smsType, failedSms: failedContacts };
                                let newJobLogs = await JobLog.add(NewJobLog);
                            } else {
                                let NewJobLog = { successSmsCount: successSmsCount, failedSmsCount: failedSmsCount, status: 'completed', job_name: result.smsType, failedSms: failedContacts };
                                let newJobLogs = await JobLog.add(NewJobLog);
                            }
                        }
                    }
                });
            }
            return result;
        } catch (err) {
            return ({ error: err.message || err.toString() });
        }
    }

    async sendLeadSms(user, { callType }) {
        try {
            let message = "";
            let successSmsCount = 0;
            let failedSmsCount = 0;
            let smsCount = 0;
            let failedContacts = [];
            let result = await lead.getLeadDetails(user, callType);
            if (result.records !== null) {
                result.records.forEach(async function (record) {
                    smsCount++;
                    let mobile = record.mobile;
                    let templateVars = templateType(result.smsType, record);
                    for (var prop in templateVars) {
                        if (templateVars[prop] === undefined) {
                            templateVars[prop] = '';
                        }
                    }
                    if (mobile === '9848549939' || mobile === '9848328970' || mobile === '9949900996') {
                        if(mobile === '9848549939'){
                            mobile = '7795659269';
                        }else if(mobile === '9848328970'){
                            mobile = '9739792292';
                        }else{
                            mobile = '9901711551'
                        }
                        console.log("The mobile sendBookingSms ", mobile);
                        let messageLog = await MsgTemplate.list(result.smsType);
                        let templateString = messageLog[0].value;
                        let messageTemplate = function (templateString, templateVars) {
                            return new Function("return `" + templateString + "`;").call(templateVars);
                        }
                        message = messageTemplate(templateString, templateVars);
                        console.log("The message is ", message);
                        let messageResponse = await sms(mobile, message);
                        console.log("The messageLogs are ", messageResponse);
                        if (messageResponse.status === 'success') {
                            successSmsCount++;
                        } else {
                            failedSmsCount++;
                            failedContacts.push(record.mobile);
                        }
                        let NewMsgLog = { name: record.partner_name, mobile: record.mobile, sms_type: result.smsType, message: message, response: messageResponse };
                        let newMsgLogs = await MsgLog.add(NewMsgLog);
                        console.log("The newMsgLogs ", newMsgLogs);
                        if (smsCount === result.length) {
                            console.log("The countttt ", smsCount, result.length);
                            if (failedSmsCount === smsCount) {
                                let NewJobLog = { successSmsCount: successSmsCount, failedSmsCount: failedSmsCount, status: 'failed', job_name: result.smsType, failedSms: failedContacts };
                                let newJobLogs = await JobLog.add(NewJobLog);
                            } else {
                                let NewJobLog = { successSmsCount: successSmsCount, failedSmsCount: failedSmsCount, status: 'completed', job_name: result.smsType, failedSms: failedContacts };
                                let newJobLogs = await JobLog.add(NewJobLog);
                            }
                        }
                    }
                });
            }
            return result;
        } catch (err) {
            return ({ error: err.message || err.toString() });
        }
    }
}

function templateType(type, record) {
    switch (type) {
        case 'Service_Booking':
            return {
                name: record.partner_name,
                vehicleModel: record.vehicle_model,
                registrationNum: record.registrationNum,
                address: record.location_id[1],
                time: record.dop
            };
        case 'Insurance_Booking':
            console.log("insideeeee service");
            return {
                name: record.partner_name,
                vehicleModel: record.vehicle_model,
                registrationNum: record.registrationNum,
                address: record.pick_up_address,
            };
        case 'Service_Lead':
            return {
                name: record.partner_name,
                vehicleModel: record.name,
                registrationNum: record.registrationNum,
                service: record.service
            }
        case 'Insurance_Lead':
            return {
                name: record.partner_name,
                vehicleModel: record.name,
                registrationNum: record.registrationNum,
                service: record.service
            }
        default:
            return {
                error: 'error'
            }
    }
}

module.exports = new Task();
