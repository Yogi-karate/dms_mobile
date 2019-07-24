export default function getRootUrl() {
  const port = process.env.PORT || 8001;
  const dev = process.env.NODE_ENV !== 'production';
  const ROOT_URL = dev ? `http://localhost:${port}` : 'https://api.turnright.tech';

  return ROOT_URL;
}