const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const msgRecipientSchema = new Schema({
    name: {
        type: String,
        default: "",
    },
    source: {
        type: Array,
        default: [],
    },
    ccAddress: {
        type: Array,
        default: [],
    },
    toAddress: {
        type: Array,
        default: [],
    },
    replyTo: {
        type: Array,
        default: [],
    },
    mobile: {
        type: Array,
        default: [],
    },
    createdAt: {
        type: Date,
    },
    type: {
        type: Array,
        default: []
    },
});

class MessageRecipientClass {
    static publicFields() {
        return ['name', 'source', 'mobile'];
    }

    static async list() {
        const msgRecipients = await this.find({})
            .sort({ createdAt: -1 });
        return msgRecipients;
    }

    static async listByName(name) {
        const recipient = await this.find({ name: name })
            .sort({ createdAt: -1 });
        return recipient;
    }

    static async add(user, { name, source, ccAddress, toAddress, replyTo, mobile, type }) {
        if (name) {
            const recipient = await this.findOne({ name });
            if (recipient) return recipient;
            const newRecipient = await this.create({
                createdAt: new Date(),
                name,
                source,
                ccAddress,
                toAddress,
                replyTo,
                mobile,
                type
            });
            return newRecipient;
        }
    };
}
msgRecipientSchema.loadClass(MessageRecipientClass);
const MessageRecipient = mongoose.model('MessageRecipient', msgRecipientSchema);
module.exports = MessageRecipient;

