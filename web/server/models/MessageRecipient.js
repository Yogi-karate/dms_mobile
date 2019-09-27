const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const msgRecipientSchema = new Schema({
    name: {
        type: String,
    },
    email: {
        type: String,
    },
    mobile: {
        type: String,
    },
    createdAt: {
        type: Date,
    },
});

class MessageRecipientClass {
    static publicFields() {
        return ['name', 'email', 'mobile'];
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

    static async add(user, { name, email, mobile }) {
        if (name) {
            const recipient = await this.findOne({ name });
            if (recipient) return recipient;
            const newRecipient = await this.create({
                createdAt: new Date(),
                name,
                email,
                mobile,
            });
            return newRecipient;
        }
    };
}
msgRecipientSchema.loadClass(MessageRecipientClass);
const MessageRecipient = mongoose.model('MessageRecipient', msgRecipientSchema);
module.exports = MessageRecipient;

