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
      'Content-type': 'application/json; charset=UTF-8'
    }
  });
  export const getTeams = (user) =>
  sendRequest(`${BASE_PATH}/customer/odoo/crm.team`, {
    method: 'GET',
    headers: {
      'Content-type': 'application/json; charset=UTF-8'
    }
  });
  export const getDailyLeads = (id) =>
  sendRequest(`${BASE_PATH}/leads/leadDashboard/`+id, {
    method: 'GET',
    headers: {
      'Content-type': 'application/json; charset=UTF-8'
    }
  });

export const addSerCategory = (serCatData) =>
  sendRequest(`${BASE_PATH}/offer/services/add`, {
    method: 'POST',
    body: JSON.stringify(serCatData)
  });

export const getLoginCreds = (userData) =>
  sendRequest(`/login`, {
    method: 'POST',
    body: JSON.stringify(userData)
  });

  export const getSalesDashboard = (user) =>
  sendRequest(`${BASE_PATH}/sales/dashboard`, {
    method: 'GET',
    headers: {
      'Content-type': 'application/json; charset=UTF-8'
    }
  });