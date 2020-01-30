const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const MsgTemplate = require('./MsgTemplate');

const jobLMasterSchema = new Schema({
    createdAt: {
        type: Date,
    },
    name: {
        type: String,
        unique: true,
    },
    msgTemplate: {
        type: Schema.ObjectId, ref: 'MsgTemplate'
    },
    action: {
        type: String,
    },
    active: {
        type: Boolean,
        default: true,
    }
});

class JobMasterClass {
    // User's public fields
    static publicFields() {
        return ['id', 'name', 'msgTemplate'];
    }
    static async list(name) {
        const populateJobMaster = [{ path: "msgTemplate" }];
        const jobMasters = await this.find({'name': name})
            .populate(populateJobMaster)
            .sort({ createdAt: -1 });
        return jobMasters;
    }
    static async add(user,{ name, msgTemplate, action }) {
        const newJobMaster = await this.create({
            createdAt: new Date(),
            name,
            msgTemplate,
            action
        });
        return newJobMaster;
    };
}
jobLMasterSchema.loadClass(JobMasterClass);
const JobMaster = mongoose.model('JobMaster', jobLMasterSchema);
module.exports = JobMaster;

