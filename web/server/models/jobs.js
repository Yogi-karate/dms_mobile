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
            let smsType = '';
            let today = new Date();
            console.log("The todayyy is ", today);
            var checkDay = new Date(2019, 7, 1);
            console.log("The checkDay is ", checkDay);
            domain.push(["create_date", ">=", checkDay]);
            domain.push(["opportunity_type", "=", "Service"]);
            domain.push(["type", "=", "lead"]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "opportunity_type"] });
        } catch (err) {
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
            let smsType = '';
            let today = new Date();
            console.log("The todayyy is ", today);
            var checkDay = new Date(2019, 7, 1);
            console.log("The checkDay is ", checkDay);
            domain.push(["create_date", ">=", checkDay]);
            domain.push(["opportunity_type", "=", "Insurance"]);
            domain.push(["type", "=", "lead"]);
            result = await server.search_read(model, { domain: domain, fields: ["name", "id", "date_deadline", "mobile", "partner_name", "opportunity_type"] });
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
            result = await server.search_read(model, { domain: [], fields: ["mobile", "partner_name", "booking_type", "dop", "vehicle_model", "location_id", "service_type", "user_id"], sort: "id desc" });
            result.records = base.cleanModels(result.records);
            return result;
        } catch (err) {
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
            result = await server.search_read(model, { domain: [], fields: ["mobile", "partner_name", "booking_type", "idv", "previous_insurance_company", "policy_no", "cur_final_premium", "cur_ncb", "cur_dip_or_comp", "pick_up_address", "rollover_company"], sort: "id desc" });
            result.records = base.cleanModels(result.records);
            return result;
        } catch (err) {
            return { error: err.message || err.toString() };
        }
    }

    async leadBookingSms(user, { callType }) {
        try {
            let smsCount = 0;
            let message = '';
            let successSmsCount = 0;
            let failedSmsCount = 0;
            let jobMaster = await JobMaster.list(callType);
            console.log("First Fetched jobMaster ", jobMaster);
            let NewJobLog = { name: callType, jobMaster: jobMaster[0]._id };
            let newJobLogs = await JobLog.add(NewJobLog);
            console.log("Second Created jobLog ", newJobLogs);
            let templateString = jobMaster[0].msgTemplate.value;
            console.log("Third fetched msgTemplate ", templateString);
            let result = await this[`execute${jobMaster[0].action}`](user);
            console.log("Fourth fetched result to loop over ", result);
            if (result.records !== null) {
                result.records.forEach(async function (record) {
                    smsCount++;
                    let mobile = record.mobile;
                    let templateVars = templateType(callType, record);
                    for (var prop in templateVars) {
                        if (templateVars[prop] === undefined) {
                            templateVars[prop] = '';
                        }
                    }
                    if (mobile === '9885008580' || mobile === '9701688777' || mobile === '9949512007') {
                        mobile = '7795659269';
                        console.log("The mobile sendBookingSms ", mobile);
                        let messageTemplate = function (templateString, templateVars) {
                            return new Function("return `" + templateString + "`;").call(templateVars);
                        }
                        message = messageTemplate(templateString, templateVars);
                        console.log("The message is ", message);
                        let messageResponse = await sms("", message);
                        console.log("The messageLogs are ", messageResponse);
                        if (messageResponse.status === 'success') {
                            successSmsCount++;
                        } else {
                            failedSmsCount++;
                        }
                        let NewMsgLog = { name: record.partner_name, mobile: record.mobile, templateName: callType, message: message, response: messageResponse, jobLog: newJobLogs._id };
                        let newMsgLogs = await MsgLog.add(NewMsgLog);
                        console.log("fifth created msgLog ", newMsgLogs);
                        if (smsCount === result.length) {
                            console.log("The countttt ", smsCount, result.length);
                            let jobUpdate = await JobLog.update(newJobLogs._id, { "status": "Completed", "successCount": successSmsCount, "failedCount": failedSmsCount });
                            console.log("The final updated joblog is ", jobUpdate);
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
                vehicleModel: record.name.split('-')[1],
                registrationNum: record.registrationNum,
                service: record.service
            }
        case 'Insurance_Lead':
            return {
                name: record.partner_name,
                vehicleModel: record.name.split('-')[1],
                registrationNum: record.registrationNum,
                service: record.service
            }
        default:
            return {
                error: 'error'
            }
    }
}

module.exports = new Jobs();