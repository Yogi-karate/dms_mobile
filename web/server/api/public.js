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

router.get('/messageLogsBasedOnDate', async (req, res) => {
  try {
    let result = await MsgLogs.msgLogsBasedOnDate({ templateName: req.query.templateName, startDate: req.query.startDate, endDate: req.query.endDate });
    console.log("The result for dailyMessageLogs are ", result);
    if (result === false) {
      let emptyExcel = {
        templateName: req.query.templateName,
        status: "Incorrect Start or End date formats, please provide valid date formats YYYY-MM-DD"
      }
      res.xls(`${req.query.templateName}_InValidDates.xlsx`, emptyExcel);
    } else if (result.length <= 0 && result === null) {
      let emptyExcel = {
        templateName: req.query.templateName,
        status: "No Messages sent today,because leads generated is zero"
      }
      res.xls(`${req.query.templateName}_EmptyRecords.xlsx`, emptyExcel);
    } else {
      res.xls(`${req.query.templateName}_${req.query.startDate}_${req.query.endDate}.xlsx`, result, { fields: ['name', 'mobile', 'templateName', 'message', 'response.status'] });
    }
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;
