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

    public static final String URL_FORGET_PASSWORD = "http://dev.lara.appycms.com/password/reset";
    public static final String URL_SCHEDULING_FAQ = "file:///android_asset/html/scheduling_faq.html";

    private ApiUrlConfig() {
        // This class is not publicly instantiable
    }

}
