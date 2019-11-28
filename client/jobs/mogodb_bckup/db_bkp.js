var SSH = require('simple-ssh');
var fs = require('fs')

exports.handler = (event) => {
    console.log("The event")


    let ssh = new SSH({
        host: '13.233.20.46',
        user: 'ec2-user',
        key: fs.readFileSync("sab_dev.pem")
    });
    console.log("This is after ssh variable");
    try {
        let dbbkpCommand = 'mongodb_backup.sh'
        console.log("Inside try")
        ssh.exec('' + dbbkpCommand, {
            out: console.log.bind(console),
            exit: function (code, stdout, stderr) {
                console.log('operation exited with code: ' + code);
                console.log('STDOUT from EC2:\n' + stdout);
                console.log('STDERR from EC2:\n' + stderr);
                //context.succeed('Success!');
            }
        }).start();
    } catch (err) {
        console.log("Inside  Error handler");
        return err;
    }
}
