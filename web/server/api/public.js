const express = require('express');
const router = express.Router();
const appVersion = require('../models/appVersion');
const MsgLogs = require('../models/MsgLog');

router.use((req, res, next) => {
  next();
});
router.get('/send', async (req, res) => {
  try {
    let result = {};
    res.json(result);
  } catch (err) {
    res.status(200).send({ message: "thanks for reaching out !!!" });
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

router.get('/dailyMessageLogs', async (req, res) => {
  try {
    let result = await MsgLogs.listDailyLogs({ templateName: req.query.templateName });
    console.log("The result for dailyMessageLogs are ", result);
    if (result.length > 0 && result !== null) {
      res.xls(`${req.query.templateName}_${result[0].createdAt}.xlsx`, result, { fields: ['name', 'mobile', 'templateName', 'message', 'response.status'] });
    } else {
      let emptyExcel = {
        templateName: req.query.templateName,
        status: "No Messages sent today,because leads generated is zero"
      }
      res.xls(`${req.query.templateName}_Empty.xlsx`, emptyExcel);
    }
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
