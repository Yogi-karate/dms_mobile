const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;

const eventMasterSchema = new Schema({
    name: {
        type: String,
        required: true
    }
});

class EventMasterClass {
    static async list(user) {
        const eventMasters = await this.find({})
            .sort({ name: -1 });
        return { eventMasters };
    }
    static async add(user, { name }) {
        console.log("Inside adding event master");
        if (name != "" && name != null) {
            const newEvent = await this.create({
                name: name
            });
            return newEvent;
        } else {
            return "name not present";
        }
    }
}
eventMasterSchema.loadClass(EventMasterClass);
const EventMaster = mongoose.model('EventMaster', eventMasterSchema);
module.exports = EventMaster;

