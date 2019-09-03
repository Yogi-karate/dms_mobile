const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;
const jobLogSchema = new Schema({
    createdAt: {
        type: Date,
    },
    smsCount: {
        type: Number,
    },
    status: {
        type: String,
    }
});

class JobLogClass {
    // User's public fields
    static publicFields() {
        return ['id', 'createdAt', 'smsCount', 'status'];
    }
    static async list() {
        const jobLogs = await this.find({})
            .sort({ createdAt: -1 });
        return jobLogs ;
    }
    static async add({ smsCount, status }) {
            const newJobLog = await this.create({
                createdAt : new Date(),
                smsCount,
                status
            });
            return newJobLog;
    };
}
jobLogSchema.loadClass(JobLogClass);
const JobLog = mongoose.model('JobLog', jobLogSchema);
module.exports = JobLog;

