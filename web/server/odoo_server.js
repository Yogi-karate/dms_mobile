const Odoo = require('./odoo');
require('dotenv').config();

class Odoo_Server {
    constructor(host, port, database, admin_user, admin_password) {
        this.host = host;
        this.port = port;
        this.admin_user = admin_user;
        this.admin_password = admin_password;
        this.connections = {};
        this.init();
    };
    init(){
        console.log("Hello from server init");
        const odoo = new Odoo({
            host: 'localhost',
            port: 8069,
            database: 'hyundai',
            username: 'admin',
            password: 'admin'
        });
        odoo.connect(function (err, result) {
            if (err) {
                console.log("Error in connecting to Odoo");
            }
            console.log("Result:", result);
        });
        this.connections['admin'] = odoo;
    }
    getOdoo(user){
        console.log(user);
        console.log(this.connections);
        console.log(this.connections.user);
        return this.connections[user];
    }
}

const server = new Odoo_Server( 'localhost',8069,'hyundai', 'admin','admin');

module.exports = server;