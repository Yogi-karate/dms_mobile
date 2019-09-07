const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const jobLogSchema = new Schema({
    createdAt: {
        type: Date,
    },
    successSmsCount: {
        type: Number,
    },
    failedSmsCount: {
        type: Number,
    },
    status: {
        type: String,
    },
    job_name: {
        type: String,
    },
    failedSms: {
        type: Array,
    }
});

class JobLogClass {
    // User's public fields
    static publicFields() {
        return ['id', 'createdAt', 'successSmsCount', 'status'];
    }
    static async list() {
        const jobLogs = await this.find({})
            .sort({ createdAt: -1 });
        return jobLogs;
    }
    static async add({ successSmsCount, failedSmsCount, status, job_name, failedSms }) {
        const newJobLog = await this.create({
            createdAt: new Date(),
            successSmsCount,
            failedSmsCount,
            status,
            job_name,
            failedSms
        });
        return newJobLog;
    };
}
jobLogSchema.loadClass(JobLogClass);
const JobLog = mongoose.model('JobLog', jobLogSchema);
module.exports = JobLog;

