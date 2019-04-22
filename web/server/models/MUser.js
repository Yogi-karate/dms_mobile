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
    pin: {
        type: Number,
        required: true,
    },
    mobile: {
        type: String,
        required: true,
        unique: true,
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

class MUserClass {
    // User's public fields
    static publicFields() {
        return ['id', 'displayName', 'mobile', 'isAdmin', 'pin'];
    }
    static async list() {
        const users = await this.find({})
            .sort({ createdAt: -1 });
        return { users };
    }
    static async add({ name, mobile, pin, address }) {
        if (mobile) {
            const user = await this.findOne({ mobile });
            if (user) return user;
            const newUser = await this.create({
                createdAt: new Date(),
                name,
                mobile,
                pin,
                address,
            });
        } else {
            console.log("ERROR in request - no mobile");
            throw new Error('User cannot be created without mobile number');
        }
    };
    static async signInOrSignUp({
        googleId, email, googleToken, displayName, avatarUrl,
    }) {
        const user = await this.findOne({ googleId }).select(UserClass.publicFields().join(' '));

        if (user) {
            const modifier = {};

            if (googleToken.accessToken) {
                modifier.access_token = googleToken.accessToken;
            }

            if (googleToken.refreshToken) {
                modifier.refresh_token = googleToken.refreshToken;
            }

            if (_.isEmpty(modifier)) {
                return user;
            }

            await this.updateOne({ googleId }, { $set: modifier });

            return user;
        }

        const newUser = await this.create({
            createdAt: new Date(),
            name,
            mobile,
            pin,
            displayName
        });

        return _.pick(newUser, UserClass.publicFields());
    }
}
muserSchema.loadClass(MUserClass);
const MUser = mongoose.model('MUser', muserSchema);
module.exports = MUser;

