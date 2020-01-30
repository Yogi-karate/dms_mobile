const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;
const locationRegSchema = new Schema({
    createdAt: {
        type: Date,
        required: true,
    },
    location_name: {
        type: String,
    },
    location_id: {
        type: Number,
    },
    latitude: {
        type: Number,
    },
    longitude: {
        type: Number,
    },
    security: [{
        type: Schema.ObjectId, ref: 'User'
    }]
});

class LocationRegistrationClass {
    // User's public fields
    static publicFields() {
        return ['id', 'name', 'security', 'createdAt'];
    }
    static async list() {
        const populateSecurity = [{ path: "security", select: ['name', 'mobile'] }];
        const locationRegistrations = await this.find({})
            .populate(populateSecurity)
            .sort({ createdAt: -1 });
        return locationRegistrations;
    }

    static async listBySecurity(userId) {
        const securityUsers = await this.find({ "security": userId })
            .sort({ createdAt: -1 })
            .select(['location_name', 'location_id']);
        return securityUsers;
    }

    static async update({ locationId }, req) {
        let uniqueSecurityArray = [];
        if (req.security != undefined) {
            uniqueSecurityArray = [...new Set(req.security)];
        }
        req.security = uniqueSecurityArray;
        const updLocationRegistration = await this.findByIdAndUpdate(locationId, { $set: req }, { new: true });
        console.log(updLocationRegistration);
        return updLocationRegistration;
    }

    static async add({ location_name, location_id, latitude, longitude, security }) {
        if (location_id) {
            const locations = await this.findOne({ location_id });
            if (locations) return "location already registered";
            const newLocationReg = await this.create({
                createdAt: new Date(),
                location_name,
                location_id,
                latitude,
                longitude,
                security
            });
            return "created successful";
        }
        else {
            console.log("ERROR in request - location id required");
            throw new Error('Location id required');
        }
    }
}
locationRegSchema.loadClass(LocationRegistrationClass);
const LocationRegistration = mongoose.model('LocationRegistration', locationRegSchema);
module.exports = LocationRegistration;