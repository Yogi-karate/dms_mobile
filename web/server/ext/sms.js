const http = require('http');
require('dotenv').config();
const jayson = require('jayson/promise');
const key = process.env.SMS_TEXTLOCAL_API_KEY;

// const sendSMS = async (number, message) => {

//   let options = {
//     "method": "GET",
//     "hostname": "api.textlocal.in",
//     "port": null,
//     "path": "/send/?numbers=" + number + "&apikey="+key+"&message=" + message,
//     "headers": {}
//   };
//   const headers = Object.assign({}, options.headers || {}, {
//     'Content-type': 'application/json; charset=UTF-8',
//   });

//   const response = await fetch(
//     `options.hostname+options.path`,
//     Object.assign({ method: 'GET'}, options, { headers }),
//   );

//   const data = await response.json();

//   if (data.error) {
//     console.log("Error in sms",data.error);
//     throw new Error(data.error);
//   }
//  console.log("response from sms server",data);
//   return data;
// }
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
    "path": "/send/?numbers=" + number + "&apikey=" + key + "&message=" + message,
    "headers": {}
  };
  console.log(options.path);
  let client = jayson.client.http(options);
  const { result, error } = await client.request('call', {});
  if (error != null) {
    console.log("Error in request", error);
    return null;
  }
  else {
    console.log("Hello from sms result",result);
    return result;
  }
};
// const sendSMS = async (number, message) => {
//   console.log("Trying to send text local SMS");
//   if (number === undefined) {
//     console.log("Cannot send sms without number")
//     return;
//   }
//   let return_val = "";
//   let options = {
//     "method": "GET",
//     "hostname": "api.textlocal.in",
//     "port": null,
//     "path": "/send/?numbers=" + number + "&apikey="+key+"&message=" + message,
//     "headers": {}
//   };
//   console.log(options.path);
//   let req = http.request(options, function (res) {
//     var chunks = [];

//     res.on("data", function (chunk) {
//       chunks.push(chunk);
//     });

//     res.on("end", function () {
//       var body = Buffer.concat(chunks);
//       console.log(body.toString());
//       return_val =  body.status
//     });
//   });
//   req.end();
//   return return_val;
// };
module.exports = sendSMS;