const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;
const msgTemplateSchema = new Schema({
    type: {
        type: String,
    },
    value: {
        type: String,
    },
    active: {
        type: Boolean,
        default: true,
    }
});

class msgTemplateClass {
    // User's public fields
    static publicFields() {
        return ['id', 'type', 'value'];
    }
    static async list(type) {
        const msgTemplates = await this.find({'type':type})
            .sort({ createdAt: -1 });
        return msgTemplates ;
    }
    static async add(user,{ type, value }) {
        console.log("The values are ",type , value);
        if (type) {
            const msgTemplate = await this.findOne({ type });
            if (msgTemplate) return msgTemplate;
            const newMsgTemplate = await this.create({
                type,
                value,
            });
            return newMsgTemplate;
        } else {
            console.log("ERROR in request - no msgTemplate");
            throw new Error('msgTemplate cannot be created');
        }
    };
}
msgTemplateSchema.loadClass(msgTemplateClass);
const MsgTemplate = mongoose.model('MsgTemplate', msgTemplateSchema);

module.exports = MsgTemplate;

