
module.exports =  function getRootUrl() {
  const port = process.env.PORT || 8000;
  const dev = process.env.NODE_ENV !== 'production';
  const ROOT_URL = dev ? `http://localhost:8001` : 'https://api.turnright.tech';

  return ROOT_URL;
}