import sendRequest from './sendRequest';

const BASE_PATH = '/api/v1';

export const getTeams = (user) =>
  sendRequest(`${BASE_PATH}/customer/roles`, {
    method: 'GET',
    headers: {
      'Content-type': 'application/json; charset=UTF-8'
    }
  });
export const getDailyLeads = (id) =>
  sendRequest(`${BASE_PATH}/leads/leadDashboard/` + id, {
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