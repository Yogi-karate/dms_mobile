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
    static async add({ name, mobile, templateName, message, response, jobLog }) {
        const newMsgLog = await this.create({
            name,
            mobile,
            templateName,
            message,
            response,
            jobLog
        });
        return newMsgLog;
    };
}
msgLogSchema.loadClass(MsgLogClass);
const MsgLog = mongoose.model('MsgLog', msgLogSchema);
module.exports = MsgLog;

