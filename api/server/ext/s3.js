const fs = require('fs');
const path = require('path');
const AWS = require('aws-sdk');


class S3Class {

  s3(fileName) {

    var s3 = new AWS.S3();
    //var filePath = "../../dms/web/static/avatar.png";

    //configuring parameters
    var params = {
      Bucket: 'tailgate.images',
      Body: fs.createReadStream(fileName),
      Key: "folder/" + Date.now() + "_" + path.basename(fileName)
    };

    s3.upload(params, function (err, data) {
      //handle error
      if (err) {
        console.log("Error", err);
        throw err;
      }
      //success
      if (data) {
        console.log("Uploaded in:", data.Location);
        return data;
      }
    });
  }

  async s3Base64(base64, imageName) {

    // Create an s3 instance
    const s3 = new AWS.S3();

    // Ensure that you POST a base64 data to your server.
    // Let's assume the variable "base64" is one.
    const base64Data = new Buffer.from(base64.replace(/^data:image\/\w+;base64,/, ""), 'base64');

    // Getting the file type, ie: jpeg, png or gif
    const type = base64.split(';')[0].split('/')[1];

    // Generally we'd have an userId associated with the image
    // For this example, we'll simulate one
    //const userId = 1;

    // With this setup, each time your user uploads an image, will be overwritten.
    // To prevent this, use a different Key each time.
    // This won't be needed if they're uploading their avatar, hence the filename, userAvatar.js.
    const params = {
      Bucket: 'tailgate.images',
      Key: "folder/" + Date.now() + "_" + imageName + ".jpg",
      Body: base64Data,
      //ACL: 'public-read',
      ContentEncoding: 'base64', // required
      ContentType: `image/${type}` // required. Notice the back ticks
    }

    // The upload() is used instead of putObject() as we'd need the location url and assign that to our user profile/database
    // see: http://docs.aws.amazon.com/AWSJavaScriptSDK/latest/AWS/S3.html#upload-property
    let location = '';
    let key = '';
    try {
      const { Location, Key } = await s3.upload(params).promise();
      location = Location;
      key = Key;
    } catch (error) {
      console.log(error)
    }

    // Save the Location (url) to your database and Key if needs be.
    // As good developers, we should return the url and let other function do the saving to database etc
    console.log("The location and key for s3 is ", location, key);

    return location;

    // To delete, see: https://gist.github.com/SylarRuby/b3b1430ca633bc5ffec29bbcdac2bd52
  }
}
module.exports = new S3Class();