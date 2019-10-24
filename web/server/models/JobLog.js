const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const jobLogSchema = new Schema({
    createdAt: {
        type: Date,
    },
    successCount: {
        type: Number,
        default: 0,
    },
    failedCount: {
        type: Number,
        default: 0,
    },
    status: {
        type: String,
        default: 'Pending',
    },
    name: {
        type: String,
    },
    jobMaster: {
        type: Schema.ObjectId, ref: 'JobMaster',
    },
});

class JobLogClass {
    // User's public fields
    static publicFields() {
        return ['id', 'createdAt', 'successCount', 'status'];
    }

    static async listById({id}) {
        const jobLogs = await this.find({'_id':id})
            .sort({ createdAt: -1 });
        return jobLogs ;
    }

    static async list() {
        const jobLogs = await this.find({})
            .sort({ createdAt: -1 });
        return jobLogs;
    }
    static async add({ successCount, failedCount, status, name, jobMaster }) {
        const newJobLog = await this.create({
            createdAt: new Date(),
            successCount,
            failedCount,
            status,
            name,
            jobMaster
        });
        return newJobLog;
    };
    static async update(id, req) {
        const updJobLog = await this.findByIdAndUpdate(id, { $set: req }, { new: true});
        console.log(updJobLog);
        return updJobLog;
    }
}
jobLogSchema.loadClass(JobLogClass);
const JobLog = mongoose.model('JobLog', jobLogSchema);
module.exports = JobLog;

