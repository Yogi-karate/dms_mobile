import getConfig from 'next/config'
const { serverRuntimeConfig, publicRuntimeConfig } = getConfig()

export const getRootUrl = () => {
  
  console.log(publicRuntimeConfig.port) // Will be available on both server and client
  const port = publicRuntimeConfig.api_port || 8000;
  const dev = publicRuntimeConfig.env !== 'production';
  const API_URL = publicRuntimeConfig.api_host;
  const host = publicRuntimeConfig.mode !='dev' ? publicRuntimeConfig.phost:publicRuntimeConfig.dhost
  console.log("the host for api is ",host) // Will only be available on the server side
  const ROOT_URL = dev ? `${API_URL}` : host;
  return ROOT_URL;
}
export const getDashboardYear = () => {
  const year = publicRuntimeConfig.dashboard_year;
  return year !=null ? year:new Date().getFullYear();
}

export const getPriceListApiUrl = () => {
  const url = publicRuntimeConfig.priceListApiUrl;
  return url !=null ? url:'dev.api.turnright.tech';
}

export const getSaleDataApiUrl = () => {
  const url = publicRuntimeConfig.saleDataApiUrl;
  return url !=null ? url:'dev.api.turnright.tech';
}