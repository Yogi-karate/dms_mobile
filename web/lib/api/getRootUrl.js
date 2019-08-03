import getConfig from 'next/config'
const { serverRuntimeConfig, publicRuntimeConfig } = getConfig()

export default function getRootUrl() {
  console.log(publicRuntimeConfig.host) // Will only be available on the server side
  console.log(publicRuntimeConfig.port) // Will be available on both server and client
  const port = publicRuntimeConfig.port || 8001;
  const dev = process.env.NODE_ENV !== 'production';
  const ROOT_URL = dev ? `http://localhost:${port}` : publicRuntimeConfig.host;
  return ROOT_URL;
}