const winston = require('winston');
const { combine, timestamp, label, prettyPrint } = winston.format;
const logger = winston.createLogger({
  level: process.env.NODE_ENV === 'production' ? 'info' : 'debug',
  format: winston.format.combine(timestamp() , prettyPrint()),
  transports: [new winston.transports.Console(), new winston.transports.File({
    level: 'info',
    filename: '/Users/tramm/dev/projects/react/dms/web/logs/all-logs.log',
    handleExceptions: true,
    json: true,
    maxsize: 5242880, //5MB
    maxFiles: 5,
    colorize: false
})],
});


module.exports = logger;
