package com.dealermanagmentsystem.constants;

public class ConstantsUrl {

    private static String BASE_URL = "http://api.turnright.tech/api/v1/customer/odoo/";

    // private static String BASE_URL = "http://10.10.10.215:8001/api/v1/customer/odoo/";

    public static String ENQUIRY = BASE_URL + "dms.enquiry";

    public static String SUB_ENQUIRY = BASE_URL + "crm.lead";

    public static String TYPES = BASE_URL + "dms.opportunity.type";

    public static String PRODUCT = "http://api.turnright.tech/api/v1/customer/search/products";

    public static String VARIANTS = "http://api.turnright.tech/api/v1/customer/search/variants";

    public static String COLORS = "http://api.turnright.tech/api/v1/customer/search/colors";

    public static String SOURCE = BASE_URL + "utm.source";

    public static String SALE_ORDER = BASE_URL + "sale.order";

    public static String FINANCIER = BASE_URL + "res.bank";

    public static String LOGIN = "http://api.turnright.tech/login";
}
