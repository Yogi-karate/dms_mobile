const express = require('express');
const router = express.Router();
const appVersion = require('../models/appVersion');

router.use((req, res, next) => {
  next();
});
router.get('/send', async (req, res) => {
  try {
    let result = {};
    res.json(result);
  } catch (err) {
    res.status(200).send({ message: "thanks for reaching out !!!"});
  }
});

router.post('/appVersion', async (req, res) => {
  try {
    let result = await appVersion.add(req.body);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
