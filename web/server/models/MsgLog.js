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
    date_deadline: {
        type: Date,
    },
    partner_name: {
        type: String,
    },
    call_type: {
        type: String,
    },
    message: {
        type: Object,
    },
    response: {
        type: String,
    }
});

class MsgLogClass {
    // User's public fields
    static publicFields() {
        return ['id', 'name', 'mobile', 'partner_name'];
    }
    static async list() {
        const msgLogs = await this.find({})
            .sort({ createdAt: -1 });
        return msgLogs ;
    }
    static async add({ name, mobile, date_deadline, partner_name, call_type, message, response }) {
        if (mobile) {
            const msgLog = await this.findOne({ mobile });
            if (msgLog) return msgLog;
            const newMsgLog = await this.create({
                name,
                mobile,
                date_deadline,
                partner_name,
                call_type,
                message,
                response
            });
            return newMsgLog;
        } else {
            console.log("ERROR in request - no MsgLog");
            throw new Error('MsgLog cannot be created');
        }
    };
}
msgLogSchema.loadClass(MsgLogClass);
const MsgLog = mongoose.model('MsgLog', msgLogSchema);
module.exports = MsgLog;

