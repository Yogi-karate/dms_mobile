import getConfig from 'next/config'
const { serverRuntimeConfig, publicRuntimeConfig } = getConfig()

export const getRootUrl = () => {
  
  console.log(publicRuntimeConfig.port) // Will be available on both server and client
  const port = publicRuntimeConfig.port || 8001;
  const dev = publicRuntimeConfig.env !== 'production';
  const host = publicRuntimeConfig.mode !='dev' ? publicRuntimeConfig.phost:publicRuntimeConfig.dhost
  console.log("the host for api is ",host) // Will only be available on the server side
  const ROOT_URL = dev ? `http://localhost:${port}` : host;
  return ROOT_URL;
}
export const getDashboardYear = () => {
  const year = publicRuntimeConfig.dashboard_year;
  return year !=null ? year:new Date().getFullYear();
}
