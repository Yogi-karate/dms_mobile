const admin = require('firebase-admin');

const pushNotifications = async (user, data) => {
// This registration token comes from the client FCM SDKs.

var registrationToken = user.device_token;

var message = {
  data: data,
  token: registrationToken
};
console.log("THe message",message);

// Send a message to the device corresponding to the provided
// registration token.
admin.messaging().send(message)
  .then((response) => {
    // Response is a message ID string.
    console.log('Successfully sent message:', response);
  })
  .catch((error) => {
    console.log('Error sending message:', error);
  });
}
module.exports = pushNotifications;