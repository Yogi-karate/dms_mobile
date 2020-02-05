import sendRequest from './sendRequest';

const BASE_PATH = '/api/v2/common/user';

export const getTeams = (user) =>
  sendRequest(`${BASE_PATH}/roles`, {
    method: 'GET',
    headers: {
      'Content-type': 'application/json; charset=UTF-8'
    }
  });

export const getLoginCreds = (userData) =>
  sendRequest(`/login`, {
    method: 'POST',
    body: JSON.stringify(userData)
  });