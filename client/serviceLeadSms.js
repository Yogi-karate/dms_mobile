const sendRequest = require('./http');

const BASE_PATH = '/api/v1/admin';

const sms = async () => {
    try {
        let user = await sendRequest(`/login`, {
            method: 'POST',
            body:JSON.stringify({
                "mobile":"1111111111",
                "password":"Sdms@1234"
            })
        });
        let token = user.token;
        console.log("Login Details",user);
        let res = await sendRequest(`${BASE_PATH}/sendLeadSms?callType=Service`, {
            method: 'GET',
            headers:{
                'Content-type': 'application/json; charset=UTF-8',
                'Authorization':"JWT "+user.token
            }
        });
        console.log("The response is ",res);
    } catch (err) {
        console.log("Error in request", err);
    }

}
module.exports = sms