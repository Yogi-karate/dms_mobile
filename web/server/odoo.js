const http = require('http')
const jayson = require('jayson');

const Odoo = function (config) {
  config = config || {};

  this.host = config.host;
  this.port = config.port || 80;
  this.database = config.database;
  this.username = config.username;
  this.password = config.password;
};

// Connect
Odoo.prototype.connect = function (cb) {
  let params = {
    db: this.database,
    login: this.username,
    password: this.password
  };

  let json = JSON.stringify({ params: params });

  let options = {
    host: this.host,
    port: this.port,
    path: '/web/session/authenticate',
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Content-Length': json.length
    }
  };

  let self = this;

  let req = http.request(options, function (res) {
    let response = '';

    res.setEncoding('utf8');

    res.on('data', function (chunk) {
      response += chunk;
    });

    res.on('end', function () {
      response = JSON.parse(response);

      if (response.error) {
        return cb(response.error, null);
      }

      self.uid = response.result.uid;
      self.sid = res.headers['set-cookie'][0].split(';')[0];
      self.session_id = response.result.session_id;
      self.context = response.result.user_context;

      return cb(null, response.result);
    });
  });

  req.write(json);
};

// Create record
Odoo.prototype.create = function (model, params, callback) {
  this._request('/web/dataset/call_kw', {
    kwargs: {
      context: this.context
    },
    model: model,
    method: 'create',
    args: [params]
  }, callback);
};

// Get record
Odoo.prototype.get = function (model, id, callback) {
  this._request('/web/dataset/call', {
    model: model,
    method: 'read',
    args: [id]
  }, callback);
};

// Update record
Odoo.prototype.update = function (model, id, params, callback) {
  if (id) {
    this._request('/web/dataset/call_kw', {
      kwargs: {
        context: this.context
      },
      model: model,
      method: 'write',
      args: [[id], params]
    }, callback);
  }
};

// Delete record
Odoo.prototype.delete = function (model, id, callback) {
  this._request('/web/dataset/call_kw', {
    kwargs: {
      context: this.context
    },
    model: model,
    method: 'unlink',
    args: [[id]]
  }, callback);
};

// Search records
Odoo.prototype.search = function (model, params, callback) {
  this._request('/web/dataset/call_kw', {
    kwargs: {
      context: this.context
    },
    model: model,
    method: 'search',
    args: [params]
  }, callback);
};

// Search_read records
Odoo.prototype.search_read = function (model, { domain = [], fields = [], limit = 0, offset = 0, sort = '' }, callback) {
  if (!fields.length) return console.error("The search_read method doesn't support an empty fields array.");
  console.log(domain);
  console.log(fields);
  this._request('/web/dataset/search_read', {
    context: this.context,
    model,
    domain,
    fields,
    limit,
    offset,
    sort,
  }, callback);
};

// Added fields_get method
Odoo.prototype.fields_get = function (model, { fields = [], attributes = {} }, callback) {
  this._request('/web/dataset/call_kw', {
    kwargs: {
      context: this.context
    },
    model,
    method: 'fields_get',
    args: [fields, attributes],
  }, callback);
};
// new request function 
// Odoo.prototype.request = function (path, params,callback) {
//     params = params || {};
  
//     let options = {
//       host: this.host,
//       port: this.port,
//       path: path || '/',
//       headers: {
//         'Content-Type': 'application/json',
//         'Accept': 'application/json',
//         'Cookie': this.sid + ';'
//       }
//     };
//     let client = jayson.client.http(options);
//     client.request('call', params, function (e, err, res) {
//       if (e || err) {
//         return e || err;
//       }
//       return res;
//     });
//   };
  
// Private functions
Odoo.prototype._request = function (path, params, callback) {
  params = params || {};

  let options = {
    host: this.host,
    port: this.port,
    path: path || '/',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Cookie': this.sid + ';'
    }
  };

  let client = jayson.client.http(options);

  client.request('call', params, function (e, err, res) {
    if (e || err) {
      return callback(e || err, null);
    }
    return callback(null, res);
  });
};
module.exports = Odoo;
