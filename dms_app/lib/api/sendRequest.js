import 'isomorphic-unfetch';
import {getRootUrl} from './config';

export default async function sendRequest(path, opts = {}) {
  const headers = Object.assign({}, opts.headers || {}, {
    'Content-type': 'application/json; charset=UTF-8',
  });
  const response = await fetch(
    `${getRootUrl()}${path}`,
    Object.assign({ method: 'POST', credentials: 'include' }, opts, { headers }),
  );
    if(response.status != 200){
    //console.log("Invalid response ",response);
    throw {"error":"Error in Api ","status":response.status};
  }
  const data = await response.json();
    if (data.error) {
    return data;
  }
  return data;
}