import getConfig from 'next/config'
const { serverRuntimeConfig, publicRuntimeConfig } = getConfig()

export default function getRootUrl() {
  console.log(serverRuntimeConfig.mySecret) // Will only be available on the server side
  console.log(publicRuntimeConfig.staticFolder) // Will be available on both server and client
  const port = process.env.PORT || 8001;
  const dev = process.env.NODE_ENV !== 'production';
  const ROOT_URL = dev ? `http://localhost:${port}` : 'https://api.turnright.tech';

  return ROOT_URL;
}