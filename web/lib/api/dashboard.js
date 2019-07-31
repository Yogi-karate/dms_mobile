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

export const getDailyLeads = (id) =>
    sendRequest(`${BASE_PATH}/leads/leadDashboard/` + id, {
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

export const getSalesDashboard = (user) =>
    sendRequest(`${BASE_PATH}/sales/dashboard`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });

export const getInventoryStock = (user) =>
    sendRequest(`${BASE_PATH}/dashboard/inventory/stock`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });

export const getPaymentAccount = (user) =>
    sendRequest(`${BASE_PATH}/leads/paymentAccDetails`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });
export const getEnqStateData = (enqState) =>
    sendRequest(`${BASE_PATH}/leads/search?state=`+enqState, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });