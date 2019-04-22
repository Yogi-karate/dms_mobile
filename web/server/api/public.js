const express = require('express');
const _ = require('lodash');

const MUser = require('../models/MUser');

const logger = require('../logs');
const router = express.Router();


router.use((req, res, next) => {
  next();
});

router.get('/users', async (req, res) => {
  try {
    const muser = await MUser.list();
    res.json(muser);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/user/add', async (req, res) => {
  try {
    const user = await MUser.add(req.body);
    res.json(user);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/offers', async (req, res) => {
  try {
    console.log(Offer);
    const books = await Offer.list();
    res.json(books);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/offers/add', async (req, res) => {
  try {
    let val = Object.assign({ userId: 1239 }, req.body);
    console.log(val);
    console.log(req);
    const offer = await Offer.add(req.body);
    res.json(offer);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/offers/list', async (req, res) => {
  try {
    const offerList = await Offer.listByCategory(req.body);
    console.log("In public API");
    console.log(offerList);
    res.json(offerList);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/services', async (req, res) => {
  try {
    const services = await categories.list();
    res.json(services);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/services/add', async (req, res) => {
  try {
    let val = Object.assign({ userId: 1239 }, req.body);
    console.log(val);
    console.log(req);
    const offer = await categories.add(req.body);
    res.json(offer);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
