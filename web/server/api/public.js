const express = require('express');
const router = express.Router();
const appVersion = require('../models/appVersion');
const MsgLogs = require('../models/MsgLog');
const smsJobs = require('../models/smsJobs');

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

/* on click message template send to email for excel notification this is called */
router.get('/messageLogsBasedOnDate', async (req, res) => {
  try {
    let result = await MsgLogs.msgLogsBasedOnDate({ name: req.query.name, startDate: req.query.startDate, endDate: req.query.endDate });
    if (result === false) {
      let emptyExcel = {
        name: req.query.name,
        status: "Incorrect Start or End date formats, please provide valid date formats YYYY-MM-DD and StartDate less than today"
      }
      res.xls(`${req.query.name}_InValidDates.xlsx`, emptyExcel);
    } else if (result.length <= 0 && result === null) {
      let emptyExcel = {
        templateName: req.query.name,
        status: "No Messages sent today,because leads generated is zero"
      }
      res.xls(`${req.query.name}_EmptyRecords.xlsx`, emptyExcel);
    } else {
      res.xls(`${req.query.name}_${req.query.startDate}_${req.query.endDate}.xlsx`, result, { fields: ['name', 'mobile', 'templateName', 'message', 'response.status'] });
    }
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

/* for manual sending of email notification */
router.get('/sendExcelNotification', async (req, res) => {
  try {
    let result = await smsJobs.executeExcelNotification(req.query.name, req.query.startDate, req.query.endDate);
    res.json(result);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
})

module.exports = router;
