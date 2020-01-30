const mongoose = require('mongoose');
const _ = require('lodash');

const { Schema } = mongoose;
const addressSchema = new Schema({
    street: {
        type: String,
        required: true,
    },
});
const userSchema = new Schema({

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
    displayName: String,
    address: [addressSchema],
});

class UserClass {
    // User's public fields
    static publicFields() {
        return ['id', 'displayName', 'mobile', 'isAdmin'];
    }
    static async list() {
        const users = await this.find({})
            .sort({ createdAt: -1 })
            .select(['_id', 'name', 'mobile']);
        return users;
    }

    static async add({ name, email, mobile, partner_id, address, isAdmin }) {
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
                isAdmin
            });
            return newUser;
        } else {
            console.log("ERROR in request - no mobile");
            throw new Error('User cannot be created without mobile number');
        }
    };

    static async updateUser(id, req) {
        const updUser = await this.findByIdAndUpdate(id, { $set: req }, { new: true });
        return updUser;
    }

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
userSchema.loadClass(UserClass);
const User = mongoose.model('User', userSchema);
module.exports = User;