// Create client with a Promise constructor
const utils = require('util');
const googleMapsClient = require('@google/maps').createClient({
  key: 'AIzaSyBPw139e5-pqPnoOr25Twfr1ZPmZT72JRw',
  Promise: Promise // 'Promise' is the native constructor.
});

function distance(location, event) {
  let finalDistance = '';
  if (location !== null && event !== null) {
    var coordinates = {
      /* origins: {
        lat: '6.535578',
        long: '3.368550'
      },
      destinations: {
        lat: '6.535578',
        long: '3.368550'
      } */
      origins: {
        lat: location.latitude,
        long: location.longitude
      },
      destinations: {
        lat: event.lat,
        long: event.long
      }
    }
  }
  let distanceDiff = googleMapsClient.distanceMatrix({
    origins: `${coordinates.origins.lat},${coordinates.origins.long}`,
    destinations: `${coordinates.destinations.lat},${coordinates.destinations.long}`,
    mode: 'transit' //mode include "driving","walking" , "bicycling", "transit"
  }).asPromise().then((response) => {
    console.log("the response object for lat long is", response);
    let rows = response.json.rows;
    if (rows[0].elements[0].status == "OK" && rows[0].elements[0].distance.value !== undefined) {
      finalDistance = utils.inspect((rows[0].elements[0].distance.value) / 1000);
      console.log("The result for distance is ", finalDistance);
      return finalDistance;
    }
  })
    .catch(err => console.log(err));
  return distanceDiff;
}

module.exports = distance;