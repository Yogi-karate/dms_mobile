import 'isomorphic-unfetch';
import { getPriceListApiUrl } from '../../lib/api/config';

export default async function sendPricelistRequest(path, opts = {}) {
  console.log("The path are", path);
  console.log("The headers are", opts);
  const response = await fetch(
    getPriceListApiUrl(),
    Object.assign({ method: 'POST'}, opts),
  );
  console.log("response as is object",response);
  if(response.status != 200){
    //console.log("Invalid response ",response);
    throw {"error":"Error in Api ","status":response.status};
  }
  const data = await response.json();
  //console.log("The data returned", data);
  if (data.error) {
    return data;
   // throw new Error(data.error);
  }

  return data;
}