package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.BuildConfig;


public class ApiUrlConfig {

    public static final String USER_LOGIN = BuildConfig.BASE_URL
            + "/588d15f5100000a8072d2945";

    public static final String USER_LOGOUT = BuildConfig.BASE_URL
            + "/588d161c100000a9072d2946";

    public static final String ENDPOINT_BLOG = BuildConfig.BASE_URL
            + "/5926ce9d11000096006ccb30";

    public static final String ENDPOINT_OPEN_SOURCE = BuildConfig.BASE_URL
            + "/5926c34212000035026871cd";

    private ApiUrlConfig() {
        // This class is not publicly instantiable
    }

}
