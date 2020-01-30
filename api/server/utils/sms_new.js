const http = require('http');
const request = require('request-promise');

require('dotenv').config();
const jayson = require('jayson/promise');
const user = process.env.SMS_MESSAGEBOX_USERNAME;
const password = process.env.SMS_MESSAGEBOX_PASSWORD;
const sender_id = process.env.SMS_MESSAGEBOX_SENDER_ID;

const sendSMS = async (number, message) => {
  console.log("Trying to send text local SMS");
  if (number === undefined) {
    console.log("Cannot send sms without number")
    return;
  }
  let path = "/sendsms.aspx?userid=" + user + "&password=" + password + "&sender=" + sender_id + "&mobileno=" +number;
  path = path+"&msg="+message
  console.log("the path is "+path);
  let options = {
    "method": "GET",
    "uri": "http://smsstreet.in/websms"+path,
    "port": null,
    "path": path,
    "headers": {}
  };
  const res = await request(options);
  let response = {};
  if(res.includes('Success')){
    response.status = "success";
    response.message = "Send Successful";
  } else {
    response.status = "error";
    response.message = "Send Failed";
  }
  console.log("response message: ",response);
  return response;
};
module.exports = sendSMS;