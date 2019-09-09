const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const jobLMasterSchema = new Schema({
    createdAt: {
        type: Date,
    },
    msgLogId: [{
        type: Schema.ObjectId, ref: 'MsgLog'
    }],
    jobLogId: [{
        type: Schema.ObjectId, ref: 'JobLog'
    }],
    active: {
        type: Boolean,
        default: true,
    }
});

class JobMasterClass {
    // User's public fields
    static publicFields() {
        return ['id', 'msgLogId', 'jobLogId'];
    }
    static async list() {
        const populateMUserVehicle = [{ path: "msgLogId" }, { path: "jobLogId" }];
        const jobMasters = await this.find({})
            .populate(populateMUserVehicle)
            .sort({ createdAt: -1 });
        return jobMasters;
    }
    static async add({ msgLogId, jobLogId }) {
        const newJobMaster = await this.create({
            createdAt: new Date(),
            msgLogId,
            jobLogId
        });
        return newJobMaster;
    };
}
jobLMasterSchema.loadClass(JobMasterClass);
const JobMaster = mongoose.model('JobMaster', jobLMasterSchema);
module.exports = JobMaster;

