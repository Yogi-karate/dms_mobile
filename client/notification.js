const sendRequest = require('./http');

const BASE_PATH = '/api/v1/tasks';

const notification = async () => {
    try {
        let user = await sendRequest(`/login`, {
            method: 'POST',
            data:{
                mobile:"1111111111",
                password:"Sdms@1234"
            }
        });
        console.log("Login Details",user);
        let res = await sendRequest(`${BASE_PATH}/list`, {
            method: 'GET',
        });
        console.log("The response is ",res);
    } catch (err) {
        console.log("Error in request", err);
    }

}
module.exports = notification