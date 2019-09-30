/**
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License. A copy of
 * the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 */

//snippet-sourcedescription:[ses_sendemail.js demonstrates how to compose an Amazon SES email and queue it for sending.]
//snippet-keyword:[JavaScript]
//snippet-sourcesyntax:[javascript]
//snippet-keyword:[Code Sample]
//snippet-keyword:[Amazon Simple Email Service]
//snippet-service:[ses]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[2018-06-02]
//snippet-sourceauthor:[AWS-JSDG]

// ABOUT THIS NODE.JS SAMPLE: This sample is part of the SDK for JavaScript Developer Guide topic at
// https://docs.aws.amazon.com/sdk-for-javascript/v2/developer-guide//ses-examples-sending-email.html

// snippet-start:[ses.JavaScript.email.sendEmail]
// Load the AWS SDK for Node.js

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
      CcAddresses: [
        ccAddress,
        /* more items */
      ],
      ToAddresses: [
        toAddress,
        /* more items */
      ]
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
    ReplyToAddresses: [
      /* more items */
    ]
  }

  // Create the promise and SES service object
  let sendPromise = new AWS.SES({ apiVersion: '2010-12-01' }).sendEmail(options).promise();

  // Handle promise's fulfilled/rejected states
  sendPromise.then(
    function (data) {
      return "success";
    }).catch(
      function (err) {
        return "error";
      });
  // snippet-end:[ses.JavaScript.email.sendEmail]

};

module.exports = sendMail;