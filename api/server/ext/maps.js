// Create client with a Promise constructor
const googleMapsClient = require('@google/maps').createClient({
    key: 'AIzaSyBPw139e5-pqPnoOr25Twfr1ZPmZT72JRw',
    Promise: Promise // 'Promise' is the native constructor.
  });
  const marker1 =  googleMapsClient.LatLng(40.714, -74.006);
  const marker2 =  googleMapsClient.LatLng(46.26, -126.3);
  console.log("the markers",marker1,marker2);
  // Geocode an address with a promise
  googleMapsClient.geocode({address: '1600 Amphitheatre Parkway, Mountain View, CA'}).asPromise()
    .then((response) => {
      console.log(response.json.results);
    })
    .catch((err) => {
      console.log(err);
    });