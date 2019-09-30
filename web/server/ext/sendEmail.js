// Load the AWS SDK for Node.js
const request = require('request-promise');
require('dotenv').config();

var AWS = require('aws-sdk');
// Set the region 
//AWS.config.update({region: 'REGION'});
AWS.config.update({
  accessKeyId: process.env.Amazon_accessKeyId,
  secretAccessKey: process.env.Amazon_secretAccessKey,
  region: process.env.Amazon_region
});


// Create sendEmail params 
const sendMail = async (from, ccAddress, toAddress, message) => {
  console.log("The sendMail params are ", from, ccAddress, toAddress, message);
  let options = {
    Destination: { /* required */
      CcAddresses:
        ccAddress,
      /* more items */
      ToAddresses:
        toAddress,
      /* more items */
    },
    Message: { /* required */
      Body: { /* required */
        Html: {
          Charset: "UTF-8",
          Data: message
        },
        Text: {
          Charset: "UTF-8",
          Data: message
        }
      },
      Subject: {
        Charset: 'UTF-8',
        Data: message
      }
    },
    Source: from, /* required */
    /* ReplyToAddresses: [
    ] */
  }


  // Create the promise and SES service object
  try {
    let sendPromise = await new AWS.SES({ apiVersion: '2010-12-01' }).sendEmail(options).promise();
    sendPromise.status = "success";
    return sendPromise;
  } catch (err) {
    err.status = "failure";
    return err.message;
  }

};

module.exports = sendMail;