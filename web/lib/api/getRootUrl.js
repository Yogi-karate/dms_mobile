import getConfig from 'next/config'
const { serverRuntimeConfig, publicRuntimeConfig } = getConfig()

export default function getRootUrl() {
  console.log(publicRuntimeConfig.host) // Will only be available on the server side
  console.log(publicRuntimeConfig.port) // Will be available on both server and client
  const port = publicRuntimeConfig.port || 8001;
  const dev = publicRuntimeConfig.env !== 'production';
  const host = publicRuntimeConfig.mode !='dev' ? publicRuntimeConfig.phost:publicRuntimeConfig.dhost
  const ROOT_URL = dev ? `http://localhost:${port}` : host;
  return ROOT_URL;
}