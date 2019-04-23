const Odoo = require('./odoo');
const User = require('./models/MUser');
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
    init() {
        console.log("Hello from server init");
        const odoo = new Odoo({
            host: 'localhost',
            port: 8069,
            database: 'hyundai',
            username: 'admin',
            password: 'admin'
        });
        let self = this;
        odoo.connect(async function (err, result) {
            if (err) {
                console.log("Error in connecting to Odoo");
            }
            let val = await self.initDatabase(odoo);
            console.log("Result:", result);
            console.log("Result--------", val);
        });
        this.connections['admin'] = odoo;
    }
    initDatabase(server) {
        console.log("Database Check ...Hang on");
        console.log(server);
        //let server = this.connections['admin'];
        server.search_read("res.users", { domain: [], fields: ["login","phone","mobile"] }, function (err, users) {
            if (err) { return console.log(err); }
            //console.log('User', users);
            let userList = users.records;
            console.log("HOLAAAAAA");
            console.log('User ...', userList[0]);
        });

    }
    getOdoo(user, password) {
        console.log(user);
        console.log(password);
        console.log(this.connections);
        console.log(this.connections[user]);
        if (this.connections[user] === undefined) {
            console.log("Creating New Odoo Session");
            this.connections[user] = new Odoo({
                host: 'localhost',
                port: 8069,
                database: 'hyundai',
                username: 'admin',
                password: 'admin'
            });
        }
        console.log(this.connections);
        return this.connections[user];
    }
}

const server = new Odoo_Server('localhost', 8069, 'hyundai', 'admin', 'admin');

module.exports = server;