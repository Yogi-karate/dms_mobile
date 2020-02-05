const express = require('express');
const _ = require('lodash');
require('dotenv').config();

const Event = require('../../../models/tailgate/Event');
const base = require('../../../models/core/base');
const logger = require('../../../logs');
const router = express.Router();
const passport = require('passport');
const store = require('../../../ext/s3');

router.use((req, res, next) => {
  console.log("service api authentication ");
  if (req.user) {
    next();
  } else {
    passport.authenticate('jwt', { session: false }, (err, user, info) => {
      if (err) {
        console.error(err);
        res.status(401).send("Unauthorized Access");
        return;
      }
      if (info !== undefined) {
        console.log(info.message);
        res.status(403).send({ "error": info.message });
        return;
      }
      req.user = user;
      next();
    })(req, res, next);
  }
});

router.get('/listEvents', async (req, res, next) => {
  try {
    console.log("Inside listEvents events api");
    const events = await Event.list();
    res.json(events);
  } catch (err) {
    next(err);
  }
});

router.get('/eventsBasedOnLocation', async (req, res, next) => {
  try {
    console.log("Inside eventsBasedOnLocation events api");
    const events = await Event.listEventsBasedOnLocation({ "locationId": req.query.locationId });
    res.json(events);
  } catch (err) {
    next(err);
  }
});

router.post('/createEvents', async (req, res, next) => {
  let createdEvent = null;
  try {
    console.log("Inside createEvents api");
    createdEvent = await Event.add(req.body);
    const vehicleImgPath = await store.s3Base64(createdEvent.vehicle_image, createdEvent.name + '_vehicle_image');
    console.log("The vehicle image path is ", vehicleImgPath);
    const invoiceImgPath = await store.s3Base64(createdEvent.invoice_image, createdEvent.name + '_invoice_image');
    console.log("The invoice image path is ", invoiceImgPath);
    const updateEvent = await Event.update({ 'eventId': createdEvent._id }, { 'invoice_image': invoiceImgPath, 'vehicle_image': vehicleImgPath });
    res.json({ "message": "created successful" });
  } catch (err) {
    if (createdEvent != null && createdEvent._id != undefined) {
      console.log("error in uploading image to s3 and deeting created event");
      const deletedEvent = await Event.delete({ 'eventId': createdEvent._id });
    }
    next(err);
  }
});

router.get('/listLocations', async (req, res, next) => {
  try {
    console.log("Inside list locations api");
    const locations = await base.listLocations(req.user);
    res.json(locations);
  } catch (err) {
    //res.json({ error: err.message || err.toString() });
    next(err);
  }
});

/* middleware to handle errors (this should be present at the end of all api's always) */
router.use((err, req, res, next) => {
  res.status(err.status || 500);
  res.json({
    message: err.message
  });
});

module.exports = router;

