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

router.get('/appVersion', async (req, res) => {
  try {
    let [result] = await appVersion.list();
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/createAppVersion', async (req, res) => {
  try {
    let result = await appVersion.add(req.body);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/jobLog/update/:id', async (req, res) => {
  try {
    const updatedJobLog = await JobLog.update(req.params.id, req.body);
    res.json({ message: "Successfully Updated" });
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
