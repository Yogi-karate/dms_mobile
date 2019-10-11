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
        try {
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
        } catch (err) {
            console.log("Error", err);
        }
    }
    async createUsers(server) {
        let result = await server.search_read("res.users", { domain: [], fields: ["login", "phone", "mobile", "partner_id", "company_id", "company_ids"] });
        let userList = result.records;
        let newUserArray = [];
        for (const user of userList) {
            try {
                let mobile = user.phone != false ? user.phone.trim() : null;
                let name = user.partner_id[1];
                let partner_id = user.partner_id[0];
                let isAdmin = true;
                let company_id = user.company_id;
                let company_ids = user.company_ids;
                if (user.login === "admin") {
                    mobile = '1111111111';
                }
                let localUser = await User.findOne({ mobile: mobile });
                if (localUser === null && mobile != null && mobile != false) {
                    console.log("This is new user and localuser is", user);
                    let newUser = { name: name, partner_id: partner_id, email: user.login, mobile: mobile, company_id: company_id, company_ids: company_ids }
                    if (user.login == 'admin') {
                        console.log("Admin user is added");
                        newUser.isAdmin = true;
                    }
                    let newUsers = await User.add(newUser);
                    newUserArray.push(newUsers);
                    console.log("The new_user are ", newUserArray);
                }
            } catch (error) {
                console.log(error);
            }
        }
        return newUserArray;
    }
    async initDatabase(server) {
        console.log("Database Check ...Hang on");
        let newUsers = await this.createUsers(server);
        console.log("New Users Added - ", newUsers);
        console.log("Successfully Initiated the User Database");
    }

    async refreshUsers(server) {
        let newUsers = await this.createUsers(server);
        return newUsers.length;
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