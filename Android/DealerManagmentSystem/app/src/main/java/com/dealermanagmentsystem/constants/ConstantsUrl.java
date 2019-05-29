package com.dealermanagmentsystem.constants;

public class ConstantsUrl {

    //private static String ROOT_URL = "http://api.turnright.tech/";

    private static String ROOT_URL = "http://dev.api.turnright.tech/";

    //private static String ROOT_URL = "http://10.10.10.215:8001/";

    private static String BASE_URL = ROOT_URL + "api/v1/customer/odoo/";

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

    public static String GET_LOST_REASON = ROOT_URL + "api/v1/customer/odoo/crm.lost.reason";

    public static String GET_STAGE = ROOT_URL + "api/v1/customer/odoo/crm.stage";

    public static String MARK_WON_LOST = ROOT_URL + "api/v1/customer/odoo/crm.lead/";

    public static String LEAD_OVERVIEW = ROOT_URL + "api/v1/leads/dashboard";

    public static String TASKS = ROOT_URL + "api/v1/leads/activity/";

    public static String ACTIVITY_COMPLETE_FEEDBACK = ROOT_URL + "api/v1/leads/activity/complete";

    public static String ACTIVITY_TYPE = ROOT_URL + "api/v1/customer/odoo/mail.activity.type";

    public static String USERS = ROOT_URL + "api/v1/customer/odoo/res.users";

    public static String CREATE_ACTIVITY = ROOT_URL + "api/v1/leads/activity/create";

    public static String LEAD_EDIT_ENQUIRIES = ROOT_URL + "api/v1/leads/enquiry/";

    public static String SEND_FCM_TOKEN = ROOT_URL + "api/v1/customer/register_token";

}
