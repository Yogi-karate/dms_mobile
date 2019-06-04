const Odoo = require('./odoo');
const User = require('./models/MUser');
require('dotenv').config();

class Odoo_Server {
    constructor(host, port, database, admin_user, admin_password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.admin_user = admin_user;
        this.admin_password = admin_password;
        this.connections = {};
        this.init();
    };
    init() {
        console.log("Hello from server init");
        const odoo = new Odoo({
            host: this.host,
            port: this.port,
            database: this.database,
            username: this.admin_user,
            password: this.admin_password
        });
        let self = this;
        odoo.connect(function (err, result) {
            if (err) {
                console.log("Error in connecting to Odoo");
            }
            self.initDatabase(odoo);
            console.log("Result:", odoo);
        });
        this.connections['admin'] = odoo;
    }
    async initDatabase(server) {
        console.log("Database Check ...Hang on");
        //create admin user in db if not exists
        let result = await server.search_read("res.users", { domain: [], fields: ["login", "phone", "mobile", "partner_id"] });
        let userList = result.records;
        this.users = {};
        let self = this;
        userList.forEach(async function (user) {
            try {
                //   console.log("The user in loop : ", user);
                let mobile = user.phone;
                let name = user.partner_id[1];
                let partner_id = user.partner_id[0];
                if (user.login === "admin") {
                    mobile = '1111111111';
                }
                let localUser = await User.findOne({ mobile: mobile });
                if (localUser === null && mobile != null && mobile !=false) {
                    let new_user = await User.add({ name: name, partner_id: partner_id, email: user.login, mobile: mobile, pin: "1234" });
                    self.users[mobile] = new_user;
                }
               
            } catch (error) {
                console.log(error);
            }
        });
        console.log("Successfully Initiated the User Database");
    }
    getOdoo(user, password) {
        if (this.connections[user] === undefined) {
            console.log("Creating New Odoo Session");
            let odoo = new Odoo({
                host: this.host,
                port: this.port,
                database: this.database,
                username: user,
                password: password
            });
            this.connections[user] = odoo;
            return odoo;
        } else {
            let server = this.connections[user];
            server.password = password;
            return server
        }
    }
}
const server = new Odoo_Server(process.env.Odoo_host, process.env.Odoo_port, process.env.Odoo_database, process.env.Odoo_user, process.env.Odoo_password);
module.exports = server;