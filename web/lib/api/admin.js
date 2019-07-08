import sendRequest from './sendRequest';

const BASE_PATH = '/api/v1';

export const getPartners = () =>
  sendRequest(`${BASE_PATH}/partners`, {
    method: 'GET',
  });
export const getDashboard = (user) =>
  sendRequest(`${BASE_PATH}/leads/dashboard`, {
    method: 'GET',
    headers: {
      'Content-type': 'application/json; charset=UTF-8',
      'Authorization': "JWT " + user.token
    }
  });

export const getLoginCreds = (userData) =>
  sendRequest(`/login`, {
    method: 'POST',
    body: JSON.stringify({ mobile: "1111111111", password: "Sdms@1234" })
  });