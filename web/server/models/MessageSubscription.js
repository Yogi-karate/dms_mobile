const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const msgSubscriptionSchema = new Schema({
    name: {
        type: String,
    },
    from: [{
        type: Schema.ObjectId, ref: "Recipient"
    }],
    ccAddress: [{
        type: Schema.ObjectId, ref: "Recipient"
    }],
    toAddress: [{
        type: Schema.ObjectId, ref: "Recipient"
    }],
    replyTo: [{
        type: Schema.ObjectId, ref: "Recipient"
    }],
    mobile: [{
        type: Schema.ObjectId, ref: "Recipient"
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

    static async listByName({ name }) {
        const populateSubscription = [{ path: "from" }, { path: "ccAddress" }, { path: "toAddress" }, { path: "replyTo" }, { path: "mobile" }, { path: "template" }];
        const msgSubscription = await this.find({ name: name })
            .populate(populateSubscription)
            .sort({ createdAt: -1 });
        return msgSubscription;
    }

    static async add(user, { name, from, ccAddress, toAddress, replyTo, mobile, type, template }) {
        if (name) {
            const subscription = await this.findOne({ name });
            if (subscription) return subscription;
            const newSubscription = await this.create({
                createdAt: new Date(),
                name,
                from,
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
const MessageSubscription = mongoose.model('Subscription', msgSubscriptionSchema);
module.exports = MessageSubscription;