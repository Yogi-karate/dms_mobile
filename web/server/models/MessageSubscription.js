const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const msgSubscriptionSchema = new Schema({
    name: {
        type: String,
    },
    source: [{
        type: Schema.ObjectId, ref: "MessageRecipient"
    }],
    ccAddress: [{
        type: Schema.ObjectId, ref: "MessageRecipient"
    }],
    toAddress: [{
        type: Schema.ObjectId, ref: "MessageRecipient"
    }],
    replyTo: [{
        type: Schema.ObjectId, ref: "MessageRecipient"
    }],
    mobile: [{
        type: Schema.ObjectId, ref: "MessageRecipient"
    }],
    createdAt: {
        type: Date,
    },
    template: {
        type: Schema.ObjectId, ref: "MsgTemplate"
    },
    type: {
        type: Array,
        default: []
    },
});

class MessageSubscriptionClass {
    static publicFields() {
        return ['name', 'source', 'mobile'];
    }

    static async list() {
        const msgSubscriptions = await this.find({})
            .sort({ createdAt: -1 });
        return msgSubscriptions;
    }

    static async listByName(name) {
        const msgSubscription = await this.find({ name: name })
            .sort({ createdAt: -1 });
        return msgSubscription;
    }

    static async add(user, { name, source, ccAddress, toAddress, replyTo, mobile, type, template }) {
        if (name) {
            const subscription = await this.findOne({ name });
            if (subscription) return subscription;
            const newSubscription = await this.create({
                createdAt: new Date(),
                name,
                source,
                ccAddress,
                toAddress,
                replyTo,
                mobile,
                type,
                template
            });
            return newSubscription;
        }
    };
}
msgSubscriptionSchema.loadClass(MessageSubscriptionClass);
const MessageSubscription = mongoose.model('MessageSubscription', msgSubscriptionSchema);
module.exports = MessageSubscription;