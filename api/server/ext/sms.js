const http = require('http');
require('dotenv').config();
const key = process.env.SMS_TEXTLOCAL_API_KEY;

const sendSMS = async (number, message) => {
  console.log("Trying to send text local SMS");
  if (number === undefined) {
    console.log("Cannot send sms without number")
    return;
  }
  let options = {
    "method": "GET",
    "hostname": "api.textlocal.in",
    "port": null,
    "path": "/send/?numbers=" + number + "&apikey="+key+"&message=" + message,
    "headers": {}
  };
  console.log(options.path);
  let req = http.request(options, function (res) {
    var chunks = [];

    res.on("data", function (chunk) {
      chunks.push(chunk);
    });

    res.on("end", function () {
      var body = Buffer.concat(chunks);
      console.log(body.toString());
    });
  });
  req.end();
};
module.exports = sendSMS;