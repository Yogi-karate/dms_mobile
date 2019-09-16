const _ = require('lodash');
const odoo = require('../odoo_server');
const base = require('../models/base');
const lead = require('../models/lead');
const MsgLog = require('../models/MsgLog');
const JobLog = require('../models/JobLog');
const JobMaster = require('../models/JobMaster');
const MsgTemplate = require('../models/MsgTemplate');
const sms = require('../ext/sms_new');

class Jobs {

    async executeServiceLeads(user) {
        console.log("Inside executeServiceLeads method");
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'dms.vehicle.lead';
            let domain = [];
            let date = new Date('2019', '8', '6');
            let today = date.toISOString().slice(0, 10);
            console.log("The todayyy is ", today);
            // domain.push(["create_date", ">", today]);
            domain.push(["opportunity_type", "=", "Service"]);
            domain.push(["type", "=", "lead"]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "opportunity_type", "service_type"], limit: 10 });
        } catch (err) {
            console.log("Error in service leads", err.stack);
            return { error: err.message || err.toString() };
        }
        return result;
    }

    async executeInsuranceLeads(user) {
        console.log("Inside executeInsuranceLeads method");
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'dms.vehicle.lead';
            let domain = [];
            let date = new Date();
            let today = date.toISOString().split('T')[0];
            console.log("The todayyy is ", today);
           // domain.push(["create_date", ">", "today"]);
            domain.push(["opportunity_type", "=", "Insurance"]);
            domain.push(["type", "=", "lead"]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "opportunity_type", "service_type"],limit:10 });
        } catch (err) {
            return { error: err.message || err.toString() };
        }
        return result;
    }

    async executeServiceBookings(user) {
        console.log("Inside executeServiceBookings method");
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'service.booking';;
            let smsType = '';
            let self = this;
            result = await server.search_read(model, { domain: [], fields: ["mobile", "partner_name", "booking_type", "dop", "vehicle_model", "pick_up_address", "service_type", "user_id"], sort: "id desc", limit:10});
            result.records = base.cleanModels(result.records);
            return result;
        } catch (err) {
            console.log(err.stack);
            return { error: err.message || err.toString() };
        }
    }

    async executeInsuranceBookings(user) {
        console.log("Inside executeInsuranceBookings method");
        let result = null;
        try {
            let server = odoo.getOdoo(user.email);
            let model = 'insurance.booking';
            let smsType = '';
            let self = this;
            result = await server.search_read(model, { domain: [], fields: ["mobile", "partner_name", "booking_type", "pick_up_address", "vehicle_model", "dop"], sort: "id desc", limit:10});
            result.records = base.cleanModels(result.records);
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }

    async executeSMS(user, { callType }) {
        try {
            let smsCount = 0;
            let successSmsCount = 0;
            let failedSmsCount = 0;
            let jobMaster = await JobMaster.list(callType);
            let NewJobLog = { name: callType, jobMaster: jobMaster[0]._id };
            let newJobLogs = await JobLog.add(NewJobLog);
            let templateString = jobMaster[0].msgTemplate.value;
            let result = await this[`execute${jobMaster[0].action}`](user);
            if (result && result.records) {
                for (let i = 0; i < result.records.length; i++) {
                    let record = result.records[i];
                    let message = '';
                    smsCount++;
                    let mobile = record.mobile.trim().substring(0, 10);
                    let templateVars = templateType(callType, record);
                    for (var prop in templateVars) {
                        if (templateVars[prop] === undefined) {
                            templateVars[prop] = '';
                        }
                    }
                    console.log("the count is ", smsCount);
                    if ((templateVars.name !== '' && templateVars.vehicleModel !== '' && !isNaN(mobile))) {
                        mobile = '7795659269';
                        let messageTemplate = function (templateString, templateVars) {
                            return new Function("return `" + templateString + "`;").call(templateVars);
                        }
                        message = messageTemplate(templateString, templateVars);
                        let messageResponse = await sms(mobile, encodeURIComponent(message));
                        console.log("the count after is ", smsCount);
                        let NewMsgLog = { name: record.partner_name, mobile: record.mobile, templateName: callType, message: message, response: messageResponse, jobLog: newJobLogs._id };
                        let newMsgLogs = await MsgLog.add(NewMsgLog);
                        if (messageResponse.status === 'success') {
                            successSmsCount++;
                        } else {
                            failedSmsCount++;
                        }
                    } else {
                        failedSmsCount++;
                        let message = "Name/vehicleModel/Mobile is empty";
                        let messageResponse = { status: 'error', message: record };
                        let NewMsgLog = { name: record.partner_name, mobile: record.mobile, templateName: callType, message: message, response: messageResponse, jobLog: newJobLogs._id };
                        let newMsgLogs = await MsgLog.add(NewMsgLog);
                    }
                }
                if (failedSmsCount > 0) {
                    let jobUpdate = await JobLog.update(newJobLogs._id, { "status": "Partial", "successCount": successSmsCount, "failedCount": failedSmsCount });
                } else {
                    let jobUpdate = await JobLog.update(newJobLogs._id, { "status": "Completed", "successCount": successSmsCount, "failedCount": failedSmsCount });
                }
                return result;
            } else {
                throw new Error(" No Records to process");
            }

        } catch (err) {
            console.log(err.stack);
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
                address: record.pick_up_address,
                time: record.dop
            };
        case 'Insurance_Booking':
            return {
                name: record.partner_name,
                vehicleModel: record.vehicle_model,
                registrationNum: record.registrationNum,
                address: record.pick_up_address,
                time: record.dop
            };
        case 'Service_Lead':
            return {
                name: record.partner_name,
                vehicleModel: record.name.split('-')[1],
                registrationNum: record.registrationNum,
                service: record.service_type
            }
        case 'Insurance_Lead':
            return {
                name: record.partner_name,
                vehicleModel: record.name.split('-')[1],
                registrationNum: record.registrationNum,
            }
        default:
            return {
                error: 'error'
            }
    }
}

module.exports = new Jobs();