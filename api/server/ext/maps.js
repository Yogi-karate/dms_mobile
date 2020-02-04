// Create client with a Promise constructor
const utils = require('util');
const googleMapsClient = require('@google/maps').createClient({
  key: 'AIzaSyBPw139e5-pqPnoOr25Twfr1ZPmZT72JRw',
  Promise: Promise // 'Promise' is the native constructor.
});
const coordinates = {
  origins: {
    lat: '6.535578',
    long: '3.368550'
  },
  destinations: {
    lat: '6.535578',
    long: '3.368550'
  }
}
googleMapsClient.distanceMatrix({
  origins: `${coordinates.origins.lat},${coordinates.origins.long}`,
  destinations: `${coordinates.destinations.lat},${coordinates.destinations.long}`,
  mode: 'driving' //other mode include "walking" , "bicycling", "transit"
}).asPromise().then((response) => {
  console.log(response)
  let rows = response.json.rows
  console.log(utils.inspect(rows[0].elements[0]));
})
  .catch(err => console.log(err));