const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const msgLogSchema = new Schema({
    name: {
        type: String,
    },
    mobile: {
        type: String,
    },
    templateName: {
        type: String,
    },
    message: {
        type: String,
    },
    response: {
        type: Object,
    },
    jobLog: {
        type: Schema.ObjectId, ref: 'JobLog'
    },
    createdAt: {
        type: Date,
    }
});

class MsgLogClass {
    // User's public fields
    static publicFields() {
        return ['id', 'name', 'mobile'];
    }
    static async list() {
        const msgLogs = await this.find({})
            .sort({ createdAt: -1 });
        return msgLogs;
    }
    static async msgLogsBasedOnDate({ name, startDate, endDate }) {
        console.log("The query paramaters received for msgLogsBasedOnDate ", startDate, endDate);
        const inputDate = await this.validateDate(startDate, endDate);
        if (inputDate) {
            console.log("The date domain for find method is ", inputDate);
            const msgLogs = await this.find({ templateName: name, createdAt: inputDate })
                .sort({ createdAt: -1 });
            return msgLogs;
        } else {
            return false;
        }

    }
    static async add({ name, mobile, templateName, message, response, jobLog }) {
        const newMsgLog = await this.create({
            createdAt: new Date(),
            name,
            mobile,
            templateName,
            message,
            response,
            jobLog
        });
        return newMsgLog;
    };
    static async validateDate(startDate, endDate) {
        let date = new Date().toISOString().slice(0, 10);
        console.log("Inside validateDate method");
        let dateDomain = {};
        let regEx = /([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))/;
        if (startDate !== undefined && startDate !== null && startDate <= date && startDate.match(regEx)) {
            console.log("startDate validation success");
            dateDomain.$gte = startDate;
        }
        if (endDate !== undefined && endDate !== null && endDate.match(regEx)) {
            console.log("endDate validation success");
            dateDomain.$lt = endDate;
        }
        if (Object.keys(dateDomain).length < 2) {
            console.log(" date validation failed ");
            return false;
        } else {
            return dateDomain;
        }
    };
}
msgLogSchema.loadClass(MsgLogClass);
const MsgLog = mongoose.model('MsgLog', msgLogSchema);
module.exports = MsgLog;

