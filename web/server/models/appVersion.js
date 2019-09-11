const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;
const appVersionSchema = new Schema({
    createdAt: {
        type: Date,
    },
    versionCode: {
        type: Number,
        unique: true,
    },
    cancellable: {
        type: Boolean,
    },
    versionName: {
        type: String,
    },
    url: {
        type: String,
    },
});

class AppVersionClass {
    // User's public fields
    static publicFields() {
        return ['id', 'createdAt', 'cancellable', 'versionName'];
    }
    static async list() {
        const appVersion = await this.find({})
            .sort({ createdAt: -1 });
        return appVersion ;
    }
    static async add({ versionCode, cancellable, versionName, url}) {
            const newAppVersion = await this.create({
                createdAt : new Date(),
                versionCode, 
                cancellable, 
                versionName, 
                url
            });
            return newAppVersion;
    };
}
appVersionSchema.loadClass(AppVersionClass);
const AppVersion = mongoose.model('AppVersion', appVersionSchema);
module.exports = AppVersion;

