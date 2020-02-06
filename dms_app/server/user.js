const fetch = require('isomorphic-unfetch');

require('dotenv').config();

const API_URL = process.env.API_URL;
const port = process.env.API_PORT || 8000;


async function getUser(opts = {}) {
    let path = 'api/v2/common/user/user'
    const headers = Object.assign({}, opts.headers || {}, {
        'Content-type': 'application/json; charset=UTF-8',
    });
    const response = await fetch(
        `${API_URL}:${port}/${path}`,
        Object.assign({ method: 'GET', credentials: 'include' }, opts, { headers }),
    );
    if (response.status != 200) {
        //console.log("Invalid response ",response);
        throw { "error": "Error in Api ", "status": response.status };
    }
    const data = await response.json();
    //console.log("The data returned", data);
    if (data.error) {
        return data;
        // throw new Error(data.error);
    }

    return data;
}
module.exports = getUser;