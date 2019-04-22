var Odoo = require('../lib/index');

var odoo = new Odoo({
  host: '13.232.47.91',
  port: 8069,
  database: 'hyundai',
  username: 'admin',
  password: 'admin'
});

// Connect to Odoo
odoo.connect(function (err) {
  if (err) { return console.log(err); }

  // Get a partner
  odoo.get('sale.order', 1000, function (err, partner) {
    if (err) { return console.log(err); }

    console.log('Partner', partner);
  });
});
