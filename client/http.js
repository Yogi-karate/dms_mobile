const fetch = require('isomorphic-unfetch');
const getRootUrl = require('./getRootUrl');

module.exports =  async function sendRequest(path, opts = {}) {
  console.log("the options",opts);
  const headers = Object.assign({}, opts.headers || {}, {
    'Content-type': 'application/json; charset=UTF-8',
  });

  const response = await fetch(
    `${getRootUrl()}${path}`,
    Object.assign({ method: 'POST'}, opts, { headers }),
  );

  const data = await response.json();

  if (data.error) {
    throw new Error(data.error);
  }

  return data;
}