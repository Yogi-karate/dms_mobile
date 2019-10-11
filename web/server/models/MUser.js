const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;
const addressSchema = new Schema({
    street: {
        type: String,
        required: true,
    },
});
const muserSchema = new Schema({

    name: {
        type: String,
        required: true,
    },
    email: {
        type: String,
    },
    device_token: {
        type: String,
    },
    mobile: {
        type: String,
        required: true,
        unique: true,
    },
    partner_id: {
        type: Number,
    },
    createdAt: {
        type: Date,
        required: true,
    },
    isAdmin: {
        type: Boolean,
        default: false,
    },
    company_id: {
        type: Array,
    },
    company_ids: {
        type: Array,
    },
    displayName: String,
    address: [addressSchema],
});

class MUserClass {
    // User's public fields
    static publicFields() {
        return ['id', 'displayName', 'mobile', 'isAdmin'];
    }
    static async list() {
        const users = await this.find({})
            .sort({ createdAt: -1 });
        return users;
    }

    static async listByAdmin() {
        const adminUsers = await this.find({"isAdmin": true})
            .sort({ createdAt: -1 });
        return adminUsers;
    }

    static async add({ name, email, mobile, partner_id, address, isAdmin, company_id, company_ids}) {
        console.log(mobile);
        console.log(name);
        if (mobile) {

            const user = await this.findOne({ mobile });
            if (user) return user;
            const newUser = await this.create({
                createdAt: new Date(),
                name,
                email,
                mobile,
                partner_id,
                address,
                isAdmin,
                company_id,
                company_ids
            });
            return newUser;
        } else {
            console.log("ERROR in request - no mobile");
            throw new Error('User cannot be created without mobile number');
        }
    };
    static async updateDeviceToken(user, { device_token }) {
        if (user) {
            user.device_token = device_token;
            return (await user.save());
        } else {
            console.log("ERROR in request - no mobile");
            throw new Error('User cannot be created without mobile number');
        }
    };
}
muserSchema.loadClass(MUserClass);
const MUser = mongoose.model('MUser', muserSchema);
module.exports = MUser;

