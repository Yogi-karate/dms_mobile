const http = require('http');
const request = require('request-promise');

require('dotenv').config();
const jayson = require('jayson/promise');
const key = process.env.SMS_TEXTLOCAL_API_KEY;

const sendSMS = async (number, message) => {
  console.log("Trying to send text local SMS");
  if (number === undefined) {
    console.log("Cannot send sms without number")
    return;
  }
  const path = "/send/?numbers=" + number + "&apikey=" + key + "&message=" + message;
  let options = {
    "method": "GET",
    "uri": "http://api.textlocal.in" + path,
    "port": null,
    "path": "/send/?numbers=" + number + "&apikey=" + key + "&message=" + message,
    "headers": {}
  };
  const response = JSON.parse(await request(options));
  console.log("response message: ",response.messages);
  return response.status;
};
module.exports = sendSMS;