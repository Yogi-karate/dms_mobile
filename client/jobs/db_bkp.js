var SSH = require('simple-ssh');
var fs = require('fs')

var ssh = new SSH({
    host: '13.232.221.182',
    user: 'admin',
    key: fs.readFileSync("sab_dev.pem")
});
var dbbkpCommand = '/opt/odoo/db_files/backup.sh'
var dbname = 'hyundai'
var filename = 'sample.sql'
ssh.exec('cd /opt/odoo/db_files').exec('ls -al', {
    out: function (stdout) {
        console.log('ls -al got:');
        console.log(stdout);
        console.log('now launching command');
}
}).exec('' + dbbkpCommand + ' '+dbname+' '+filename, {
    out: console.log.bind(console),
    exit: function(code, stdout, stderr) {
        console.log('operation exited with code: ' + code);
        console.log('STDOUT from EC2:\n' + stdout);
        console.log('STDERR from EC2:\n' + stderr);
       // context.succeed('Success!');
    }
}).start();

