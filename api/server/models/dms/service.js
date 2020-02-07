const _ = require('lodash');
const odoo = require('../core/odoo_server');
const base = require('../core/base');

class Service {

    async getDashboardCounts(user) {
        let result = [];
        let server = odoo.getOdoo(user.email);
        let model = 'service.booking';
        let states = ["new", "won", "lost"];
        let self = this;
        for (let i = 0; i < states.length; i++) {
            let group = await server.search(model, { domain: this.getServiceDomain(states[i]) }, true);
            result.push({ state: states[i], result: group });
        };
        return result;
    }
    getServiceDomain(state) {
        let domain = [];
        var today = new Date().toISOString().slice(0, 10);
        switch (state) {
            case "new":
                domain.push(["status", "=", "new"]);
                break;
            case "lost":
                domain.push(["status", "=", "lost"]);
                break;
            case "won":
                domain.push(["status", "=", "won"]);
                break;
        }
        return domain;
    }

    async searchBookings(user, { status }) {
        let model = 'service.booking';
        let bookingDetails = null;
        let server = odoo.getOdoo(user.email);
        let domain = []
        if (status) {
            console.log("the state is ",status);
            domain.push(["status", "=", status]);
        }
        bookingDetails = await server.search_read(model, { domain: domain, fields: ["mobile", "partner_name", "booking_type", "dop", "vehicle_model", "location_id", "service_type", "user_id"], sort: "id desc" });
        if(!bookingDetails) return {result:null}
        bookingDetails.records = base.cleanModels(bookingDetails.records);
        return { result: bookingDetails };
    }
    async serviceBookingDetails(user, { callType }) {
        let bookingDetails = null;
        let server = odoo.getOdoo(user.email);
        let model = '';
        if (callType === 'Service') {
            model = 'service.booking';
            let self = this;
            bookingDetails = await server.search_read(model, { domain: [], fields: ["mobile", "partner_name", "booking_type", "dop", "vehicle_model", "location_id", "service_type", "user_id"], sort: "id desc" });
            bookingDetails.records = base.cleanModels(bookingDetails.records);
        } else {
            model = 'insurance.booking';
            let self = this;
            bookingDetails = await server.search_read(model, { domain: [], fields: ["mobile", "partner_name", "booking_type", "idv", "previous_insurance_company", "policy_no", "cur_final_premium", "cur_ncb", "cur_dip_or_comp", "pick_up_address", "rollover_company"], sort: "id desc" });
            bookingDetails.records = base.cleanModels(bookingDetails.records);
        }
        return { result: bookingDetails };
    }


    async searchLeadsByState(user, { state, callType, userId }) {
        let result = null;
        let server = odoo.getOdoo(user.email);
        let model = 'dms.vehicle.lead';
        let domain = [];
        if (state != null) {
            domain = this.getActivityDomain(state, callType);
        }
        if (userId != null && !isNaN(userId) && userId != '') {
            domain.push(["user_id", "=", parseInt(userId)]);
        }
        result = await server.search_read(model, { domain: domain, fields: ["name", "id", "current_due_date", "mobile", "partner_name", "user_id", "team_id", "stage_id", "call_state", "disposition"] });
        result.records.forEach(record => {
            record.activity_date_deadline = record.current_due_date;
            delete record.current_due_date;
        });
        result.records.sort(function (record1, record2) {
            var dateA = new Date(record1.activity_date_deadline), dateB = new Date(record2.activity_date_deadline)
            return dateA - dateB //sort by date ascending
        });
        result.records = base.cleanModels(result.records);
        return result;
    }
}

module.exports = new Service();
