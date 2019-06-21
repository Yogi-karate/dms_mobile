const admin = require('firebase-admin');

const pushNotifications = async (user, data) => {
// This registration token comes from the client FCM SDKs.

var registrationToken = user.device_token;
console.log("The registrationToken of user is ",user);
console.log("The registrationToken is ",registrationToken);
if(registrationToken == null || registrationToken == undefined) {
 console.log("registrationToken is null or undefined");
  return null;
}
var message = {
  data: data,
  token: registrationToken
};
console.log("THe message",message);

// Send a message to the device corresponding to the provided
// registration token.
let response  = await admin.messaging().send(message);
return response;
}
module.exports = pushNotifications;