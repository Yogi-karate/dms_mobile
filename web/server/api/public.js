const express = require('express');
const router = express.Router();


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
module.exports = router;
