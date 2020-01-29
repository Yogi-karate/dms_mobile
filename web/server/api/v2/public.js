const express = require('express');
const router = express.Router();
const appVersion = require('../../models/appVersion');
const JobLog = require('../../models/JobLog');

router.use((req, res, next) => {
  next();
});
router.get('/send', async (req, res, next) => {
  try {
    let result = {};
    res.json(result);
  } catch (err) {
    res.status(200).send({ message: "thanks for reaching out !!!" });
  }
});

router.get('/appVersion', async (req, res, next) => {
  try {
    let [result] = await appVersion.list();
    res.json(result);
  } catch (err) {
    next(err);
  }
});

router.post('/createAppVersion', async (req, res, next) => {
  try {
    let result = await appVersion.add(req.body);
    res.json(result);
  } catch (err) {
    next(err);
  }
});

router.post('/jobLog/update/:id', async (req, res, next) => {
  try {
    const updatedJobLog = await JobLog.update(req.params.id, req.body);
    res.json({ message: "Successfully Updated" });
  } catch (err) {
    logger.error(err);
    next(err);
  }
});

module.exports = router;
