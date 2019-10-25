import sendRequest from './sendRequest';
import sendPricelistRequest from './pricelist';
import sendSaleDataRequest from './saleDataRequest';

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
export const getStageCounts = () =>
    sendRequest(`${BASE_PATH}/leads/stageCount`, {
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
    sendRequest(`${BASE_PATH}/leads/search?state=` + enqState, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });
export const getUserCount = (team, id, month, year) =>
    sendRequest(`${BASE_PATH}/leads/dailyLeadsNew/` + team + '/' + id + '/' + month + '/' + year, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });
export const getDailyLeads = (team, month, year, ) =>
    sendRequest(`${BASE_PATH}/leads/leadDashboards/` + team + '/' + month + '/' + year, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });
export const getCompanies = (user) =>
    sendRequest(`${BASE_PATH}/customer/odoo/res.company`, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });

/* this is used to get the product_pricelist data to display in Items table*/
export const priceListItems = (name) =>
    sendRequest(`${BASE_PATH}/sales/priceListItems?name=` + name, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });


export const priceListUpload = (data) =>
    sendPricelistRequest('', {
        method: 'POST',
        body: data
    });

export const saleDataUpload = (data) =>
    sendSaleDataRequest('', {
        method: 'POST',
        body: data
    });


export const createJobLog = (jobLogBody) =>
    sendRequest(`${BASE_PATH}/admin/createJobLog`, {
        method: 'POST',
        body: JSON.stringify(jobLogBody)
    });

export const getJobMaster = (name) =>
    sendRequest(`${BASE_PATH}/admin/listJobMaster/` + name, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });

export const getJobLog = (id) =>
    sendRequest(`${BASE_PATH}/admin/getJobLog/` + id, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        }
    });



