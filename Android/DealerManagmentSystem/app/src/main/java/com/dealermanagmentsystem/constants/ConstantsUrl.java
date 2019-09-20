package com.dealermanagmentsystem.constants;


import com.dealermanagmentsystem.preference.DMSPreference;

import static com.dealermanagmentsystem.constants.Constants.KEY_URL;

public class ConstantsUrl {

    //private static String ROOT_URL = "http://prod-api.turnright.tech/";

  // public static String ROOT_URL = "http://dev.api.turnright.tech/";

   public static String ROOT_URL =  DMSPreference.getString(KEY_URL);

   //public static String ROOT_URL = "http://192.168.0.45:8000/";

    private static String BASE_URL = ROOT_URL + "api/v1/customer/odoo/";

    public static String END_POINT_URL = "https://290dupbwpk.execute-api.ap-south-1.amazonaws.com/stage/devapp";

    public static String ENQUIRY = BASE_URL + "dms.enquiry";

    public static String SUB_ENQUIRY = BASE_URL + "crm.lead";

    public static String TYPES = BASE_URL + "dms.opportunity.type";

    public static String PRODUCT = ROOT_URL + "api/v1/customer/search/products";

    public static String VARIANTS = ROOT_URL + "api/v1/customer/search/variants";

    public static String COLORS = ROOT_URL + "api/v1/customer/search/colors";

    public static String SOURCE = BASE_URL + "utm.source";

    public static String SALE_ORDER = BASE_URL + "sale.order";

    public static String FINANCIER = BASE_URL + "res.bank";

    public static String LOGIN = ROOT_URL + "login";

    public static String LEADS = ROOT_URL + "api/v1/leads/search";

    //public static String GET_LOST_REASON = ROOT_URL + "api/v1/customer/odoo/crm.lost.reason";
    public static String GET_LOST_REASON = ROOT_URL + "api/v1/leads/lostReasons?type=Sales";

    public static String GET_STAGE = ROOT_URL + "api/v1/customer/odoo/crm.stage";

    public static String MARK_WON_LOST = ROOT_URL + "api/v1/customer/odoo/crm.lead/";

    public static String LEAD_OVERVIEW = ROOT_URL + "api/v1/leads/dashboard";

    public static String SALES_OVERVIEW = ROOT_URL + "api/v1/sales/dashboard";

    public static String DELIVERY_COUNT = ROOT_URL + "api/v1/dashboard/inventory";

    public static String INVOICE_COUNT = ROOT_URL + "api/v1/dashboard/invoice";

    public static String TASKS_OVERVIEW = ROOT_URL + "api/v1/tasks/list";

    public static String TASKS = ROOT_URL + "api/v1/leads/activity/";

    public static String ACTIVITY_COMPLETE_FEEDBACK = ROOT_URL + "api/v1/leads/activity/complete";

    public static String ACTIVITY_TYPE = ROOT_URL + "api/v1/customer/odoo/mail.activity.type";

    public static String USERS = ROOT_URL + "api/v1/customer/odoo/res.users";

    public static String CREATE_ACTIVITY = ROOT_URL + "api/v1/leads/activity/create";

    public static String EDIT_ACTIVITY = ROOT_URL + "api/v1/customer/odoo/mail.activity/";

    public static String LEAD_EDIT_ENQUIRIES = ROOT_URL + "api/v1/leads/enquiry/";

    public static String SEND_FCM_TOKEN = ROOT_URL + "api/v1/customer/register_token";

    public static String SALE_ORDER_SEARCH = ROOT_URL + "api/v1/sales/search?";

    public static String DELIVERY = ROOT_URL + "api/v1/sales/inventory/search?state=confirmed";

    public static String TEAM_DETAIL = ROOT_URL + "api/v1/leads/leadDashboard/";

    public static String USER_DETAIL = ROOT_URL + "api/v1/leads/dailyLeads/";

    public static String USER_DETAIL_BOOKED = ROOT_URL + "api/v1/leads/dailyBookedLeads/";

    public static String PAYMENT_DETAIL = ROOT_URL + "api/v1/leads/paymentAccDetails";

    public static String LOAD_USERS = ROOT_URL + "api/v1/admin/users";

    public static String SERVICE_LEAD_OVERVIEW = ROOT_URL + "api/v1/vehicleLeads/dashboard?callType=Service";

    public static String SERVICE_LEADS = ROOT_URL + "api/v1/vehicleLeads/search";

    public static String MARK_SERVICE_LOST = ROOT_URL + "api/v1/customer/odoo/dms.vehicle.lead/";

    public static String SERVICE_TASKS = ROOT_URL + "api/v1/vehicleLeads/activity/";

    public static String ACTIVITY_COMPLETE_SERVICE_FEEDBACK = ROOT_URL + "api/v1/vehicleLeads/activity/complete";

    public static String CREATE_SERVICE_ACTIVITY = ROOT_URL + "api/v1/vehicleLeads/activity/create";

    public static String SERVICE_BOOKING_COUNT = ROOT_URL + "api/v1/vehicleLeads/serviceBookingCount?callType=Service";

    public static String SERVICE_BOOKING = ROOT_URL + "api/v1/vehicleLeads/serviceBookingDetails?callType=Service";

    public static String INSURANCE_LEAD_OVERVIEW = ROOT_URL + "api/v1/vehicleLeads/dashboard?callType=Insurance";

    public static String INSURANCE_BOOKING_COUNT = ROOT_URL + "api/v1/vehicleLeads/serviceBookingCount?callType=Insurance";

    public static String INSURANCE_BOOKING = ROOT_URL + "api/v1/vehicleLeads/serviceBookingDetails?callType=Insurance";

    //public static String GET_INSURANCE_LOST_REASON = ROOT_URL + "api/v1/customer/odoo/dms.lost.reason.insurance";
    public static String GET_INSURANCE_LOST_REASON = ROOT_URL + "api/v1/leads/lostReasons?type=Insurance";

    //public static String GET_SERVICE_LOST_REASON = ROOT_URL + "api/v1/customer/odoo/dms.lost.reason";
    public static String GET_SERVICE_LOST_REASON = ROOT_URL + "api/v1/leads/lostReasons?type=Service";

    public static String CREATE_SERVICE_BOOKING = ROOT_URL + "api/v1/customer/odoo/service.booking";

    public static String SERVICE_LOCATION = ROOT_URL + "api/v1/customer/odoo/stock.location";

    public static String INSURANCE_BANK_LIST = ROOT_URL + "api/v1/customer/odoo/res.insurance.company";

    public static String CREATE_INSURANCE_BOOKING = ROOT_URL + "api/v1/customer/odoo/insurance.booking";

    public static String APP_UPDATE = ROOT_URL + "api/v1/public/appVersion";

    public static String DISPOSITION = ROOT_URL + "api/v1/customer/odoo/dms.lead.disposition";

}
