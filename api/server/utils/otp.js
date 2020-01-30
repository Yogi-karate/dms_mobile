const Speakeasy = require("speakeasy");
const Sms = require('./sms');
secret = "abc123";

class Otp {
    
    static async generate(){
        secret = Speakeasy.generateSecret({ length: 6 });
        return { "secret": secret };

    };
    static async verify() {
        console.log(secret);
        //secret_val = await Speakeasy.generateSecret({ length: 6 }); 
        return {
            "token": Speakeasy.totp({
                secret: secret,
                encoding: "base32"
            }),
            "remaining": (30 - Math.floor((new Date()).getTime() / 1000.0 % 30))
        };
    };
    static async validate({token}){
        return Speakeasy.totp.verify({
            secret: secret,
            encoding: "base32",
            token: token,
            window: 0
        });
    };
};
module.exports = Otp;
