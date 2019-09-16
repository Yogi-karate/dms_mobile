const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;
const msgTemplateSchema = new Schema({
    name: {
        type: String,
        unique: true,
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
        return ['id', 'name', 'value'];
    }
    static async list({name}) {
        const msgTemplates = await this.find({'name':name})
            .sort({ createdAt: -1 });
        return msgTemplates ;
    }
    static async add(user,{ name, value }) {
        console.log("The values are ",name , value);
        if (name) {
            const msgTemplate = await this.findOne({ name });
            if (msgTemplate) return msgTemplate;
            const newMsgTemplate = await this.create({
                name,
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

