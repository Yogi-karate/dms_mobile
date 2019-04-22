/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const { Schema } = mongoose;

const mongoSchema = new Schema({
  manufacturer: {
    type: String,
    required: true,
  },
  model: {
    type: String,
    required: true,
  },
  color: {
    type: String,
    required: true,
  },
  variant: {
    type: String,
    required: true,
  },
  active: {
    type: Boolean,
  }
});

class VehicleClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    const vehicles = await this.find({})
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url":STATIC_HOST,"vehicles":vehicles };
  }
  static async add({ manufacturer, model, color, variant, active }) {
    return this.create({
      manufacturer,
      model,
      color,
      variant,
      active
    });
  }
}
mongoSchema.loadClass(VehicleClass);

const Vehicle = mongoose.model('Vehicle', mongoSchema);

module.exports = Vehicle;

