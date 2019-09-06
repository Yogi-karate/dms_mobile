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
  let path = "/sendurlcomma.aspx?user=" + user + "&pwd=" + password + "&senderid=" + sender_id+"&mobileno="+number;
  path = path+"&msgtext="+message+"&smstype=0/4/3"
  console.log("the path is "+path);
  let options = {
    "method": "GET",
    "uri": "http://car.myinboxmedia.com"+path,
    "port": null,
    "path": path,
    "headers": {}
  };
  const res = await request(options);
  let response = {};
  if(res.trim() == 'Send Successful'){
    response.status = "success";
  } else {
    response.status = "error";
  }
  response.message = res.trim();
  console.log("response message: ",response);
  return response;
};
module.exports = sendSMS;