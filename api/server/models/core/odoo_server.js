const Odoo = require('./odoo');
const User = require('./MUser');
require('dotenv').config();

class Odoo_Server {
    constructor(host, port, database, admin_user, admin_password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.admin_user = admin_user;
        this.admin_password = admin_password;
        this.connections = {};
       // this.init();
    };
    async init() {
        try {
            console.log("Hello from server init");
            const odoo = new Odoo({
                host: this.host,
                port: this.port,
                database: this.database,
                username: this.admin_user,
                password: this.admin_password
            });
            let result = await odoo.connect_new();
            let res = await this.initDatabase(odoo);
            this.connections[this.admin_user] = odoo;
            return odoo;
        } catch (err) {
            console.log("Error", err);
            throw err;
        }
    }
    async createUsers(server) {
        let result = await server.search_read("res.users", { domain: [], fields: ["login", "phone", "mobile", "partner_id", "create_date"] });
        let userList = result.records;
        let modifiedUserCountArray = [];
        let newUserArray = [];
        let updatedUserArray = [];
        for (const user of userList) {
            try {
                let mobile = user.phone != false ? user.phone.trim() : null;
                let name = user.partner_id[1];
                let partner_id = user.partner_id[0];
                let email = user.login;
                if (email === this.admin_user) {
                    mobile = '1111111111';
                }
                let localUser = await User.findOne({ mobile: mobile });
                if (localUser === null && mobile != null && mobile != false) {
                    console.log("This is new user and not found in mongoDB ", user);
                    let newUser = { name: name, partner_id: partner_id, email: email, mobile: mobile }
                    if (email == this.admin_user) {
                        console.log("Admin user is added");
                        newUser.isAdmin = true;
                    }
                    let newUsers = await User.add(newUser);
                    newUserArray.push(newUsers);
                    console.log("The new_user are ", newUserArray);
                } else if (localUser !== null) {
                    if (localUser.name !== name || localUser.email !== email || localUser.partner_id !== partner_id) {
                        console.log("The mongoDB user that should be updated is ", localUser.mobile);
                        let dupUsers = null;
                        let mostRecentDate = [];
                        let mostRecentUser = [];
                        console.log("get the duplicate users of passed mobile number from odoo db result");
                        /* let domain = [];
                        domain.push(["phone", "=", mobile]);
                        dupUsers = await server.search_read("res.users", { domain: domain, fields: ["login", "phone", "mobile", "partner_id", "create_date"] });
                        console.log("The dupUsers areeeeeeeeeeeeeeeeeeeeeee",dupUsers); */
                        dupUsers = userList.filter(user => user.phone === mobile);
                        console.log("The dupUsers are", dupUsers, dupUsers.length);
                        if (dupUsers !== null && dupUsers.length > 0) {
                            console.log("find the latest user from odoo for updating mongoDB existing user");
                            mostRecentDate = new Date(Math.max.apply(null, dupUsers.map(function (e) {
                                return new Date(e.create_date);
                            })));
                            mostRecentUser = dupUsers.filter(e => {
                                var d = new Date(e.create_date);
                                return d.getTime() == mostRecentDate.getTime();
                            })[0];
                        }
                        console.log("get the latest user details and update the mongo user based on id");
                        let updateDetails = { name: mostRecentUser.partner_id[1], partner_id: mostRecentUser.partner_id[0], email: mostRecentUser.login };
                        let updatedUser = await User.updateUser(localUser._id, updateDetails);
                        console.log("The final updated user in mongoDB is ", updatedUser);
                        updatedUserArray.push(updatedUser);
                    }
                }
            } catch (error) {
                console.log(error);
            }
        }
        modifiedUserCountArray.push(newUserArray.length);
        modifiedUserCountArray.push(updatedUserArray.length);
        return modifiedUserCountArray;
    }
    async initDatabase(server) {
        console.log("Database Check ...Hang on");
        let newUsers = await this.createUsers(server);
        console.log("New Users Added - ", newUsers);
        console.log("Successfully Initiated the User Database");
    }

    async refreshUsers(server) {
        let newUsers = await this.createUsers(server);
        return newUsers;
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