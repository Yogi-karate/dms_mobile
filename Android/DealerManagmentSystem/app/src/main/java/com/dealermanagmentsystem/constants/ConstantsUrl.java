package com.dealermanagmentsystem.constants;

public class ConstantsUrl {

    private static String BASE_URL = "http://dev.api.turnright.tech/api/v1/customer/odoo/";

    // private static String BASE_URL = "http://10.10.10.215:8001/api/v1/customer/odoo/";

    public static String ENQUIRY = BASE_URL + "dms.enquiry";

    public static String SUB_ENQUIRY = BASE_URL + "crm.lead";

    public static String TYPES = BASE_URL + "dms.opportunity.type";

    public static String PRODUCT = "http://dev.api.turnright.tech/api/v1/customer/search/products";

    public static String VARIANTS = "http://dev.api.turnright.tech/api/v1/customer/search/variants";

    public static String COLORS = "http://dev.api.turnright.tech/api/v1/customer/search/colors";

    public static String SOURCE = BASE_URL + "utm.source";

    public static String SALE_ORDER = BASE_URL + "sale.order";

    public static String FINANCIER = BASE_URL + "res.bank";

    public static String LOGIN = "http://dev.api.turnright.tech/login";

    public static String LEADS = "http://dev.api.turnright.tech/api/v1/leads/search";

    public static String GET_LOST_REASON = "http://dev.api.turnright.tech/api/v1/customer/odoo/crm.lost.reason";

    public static String GET_STAGE = "http://dev.api.turnright.tech/api/v1/customer/odoo/crm.stage";

    public static String MARK_WON_LOST = "http://dev.api.turnright.tech/api/v1/customer/odoo/crm.lead/";

    public static String LEAD_OVERVIEW = "http://dev.api.turnright.tech/api/v1/leads/dashboard";

    public static String TASKS = "http://dev.api.turnright.tech/api/v1/leads/activity/";

    public static String ACTIVITY_COMPLETE_FEEDBACK = "http://dev.api.turnright.tech/api/v1/leads/activity/complete";

    public static String ACTIVITY_TYPE = "http://dev.api.turnright.tech/api/v1/customer/odoo/mail.activity.type";

    public static String USERS = "http://dev.api.turnright.tech/api/v1/customer/odoo/res.users";

    public static String CREATE_ACTIVITY = "http://dev.api.turnright.tech/api/v1/leads/activity/create";

    public static String LEAD_EDIT_ENQUIRIES = "http://dev.api.turnright.tech/api/v1/leads/enquiry/";



}
