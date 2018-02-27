package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.BuildConfig;


public class ApiUrlConfig {

    public static final String USER_LOGIN = BuildConfig.BASE_URL
            + "api/login";

    public static final String USER_SIGN_UP = BuildConfig.BASE_URL
            + "api/signup";

    public static final String USER_LOGOUT = BuildConfig.BASE_URL
            + "/588d161c100000a9072d2946";

    public static final String ENDPOINT_BLOG = BuildConfig.BASE_URL
            + "/5926ce9d11000096006ccb30";

    public static final String ENDPOINT_OPEN_SOURCE = BuildConfig.BASE_URL
            + "/5926c34212000035026871cd";

    public static final String APPOINTMENT_CREATE = BuildConfig.BASE_URL
            + "api/create/appointment";

    public static final String APPOINTMENT_GET = BuildConfig.BASE_URL
            + "api/read/appointment";

    public static final String APPOINTMENT_DELETE = BuildConfig.BASE_URL
            + "api/delete/appointment";

    public static final String ORDER_GET = BuildConfig.BASE_URL
            + "api/read/order";

    public static final String ORDER_EDIT = BuildConfig.BASE_URL
            + "api/edit/order";

    public static final String ORDER_COMPLETED = BuildConfig.BASE_URL
            + "api/mark/ordercomplete";

    public static final String RECEIPT_GET = BuildConfig.BASE_URL
            + "api/read/receipt";

    public static final String USER_GET = BuildConfig.BASE_URL
            + "api/read/user";

    public static final String VERIFY_USER = BuildConfig.BASE_URL
            + "api/verify/user";

    public static final String VERIFY_TRUE = BuildConfig.BASE_URL
            + "api/verify/true";

    public static final String PRODUCT_PER_CATEGORY_GET = BuildConfig.BASE_URL
            + "api/appyproduct/get/product/percategory";

    public static final String URL_FORGET_PASSWORD = "http://dev.lara.appycms.com/password/reset";
    public static final String URL_SCHEDULING_FAQ = "file:///android_asset/html/scheduling_faq.html";
    public static final String URL_AIR_CON_TYPE_INFO = "file:///android_asset/html/air_con_type_info.html";
    public static final String URL_AIR_CON_SIZE_INFO = "file:///android_asset/html/air_con_size_info.html";
    public static final String URL_CLEANING_SUPPLIES = "file:///android_asset/html/cleaning_supplies.html";
    public static final String URL_TERMS_CONDITIONS = "http://appyhomeplus.com/terms-conditions";
    public static final String URL_OUR_FAQ = "file:///android_asset/html/our_faq.html";
    public static final String URL_PRIVACY_POLICY = "http://appyhomeplus.com/privacy-policy/";

    private ApiUrlConfig() {
        // This class is not publicly instantiable
    }

}
