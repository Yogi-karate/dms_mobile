const express = require('express');
const _ = require('lodash');

const User = require('../../../models/core/MUser');
const logger = require('../../../logs');
const router = express.Router();
const passport = require('passport');

router.use((req, res, next) => {
  console.log("service api authenication ");
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
        console.log(req);
        console.log(info.message);
        res.status(403).send({ "error": info.message });
        return;
      }
      console.log(user);
      req.user = user;
      next();
    })(req, res, next);
  }
});


router.get('/users', async (req, res, next) => {
  try {
    const user = await User.list();
    res.json(user);
  } catch (err) {
    next(err);
    //res.json({ error: err.message || err.toString() });
  }
});

router.post('/users/add', async (req, res, next) => {
  try {
    const user = await User.add(req.body);
    res.json({ message: "Successfully Added" });
  } catch (err) {
    logger.error(err);
    next(err);
  }
});

router.post('/users/update', async (req, res, next) => {
  try {
    const user = await User.update(req.user, req.body);
    res.json({ message: "Successfully Updated" });
  } catch (err) {
    logger.error(err);
    next(err);
  }
});

router.post('/users/delete', async (req, res, next) => {
  try {
    const myParams = req.body;
    const user = await User.delete(myParams.id);
    res.json({ message: "Successfully Deleted" });
  } catch (err) {
    logger.error(err);
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

