package com.appyhome.appyproduct.mvvm.data.remote;

import com.appyhome.appyproduct.mvvm.BuildConfig;


public class ApiUrlConfig {

    public static final String API_USER_LOGIN = BuildConfig.BASE_URL
            + "api/login";

    public static final String API_USER_SIGN_UP = BuildConfig.BASE_URL
            + "api/signup";

    public static final String API_USER_LOGOUT = BuildConfig.BASE_URL
            + "/588d161c100000a9072d2946";

    public static final String API_ENDPOINT_BLOG = BuildConfig.BASE_URL
            + "/5926ce9d11000096006ccb30";

    public static final String API_ENDPOINT_OPEN_SOURCE = BuildConfig.BASE_URL
            + "/5926c34212000035026871cd";

    public static final String API_APPOINTMENT_CREATE = BuildConfig.BASE_URL
            + "api/create/appointment";

    public static final String API_APPOINTMENT_GET = BuildConfig.BASE_URL
            + "api/read/appointment";

    public static final String API_APPOINTMENT_DELETE = BuildConfig.BASE_URL
            + "api/delete/appointment";

    public static final String API_SERVICE_ORDER_GET = BuildConfig.BASE_URL
            + "api/read/order";

    public static final String API_SERVICE_ORDER_EDIT = BuildConfig.BASE_URL
            + "api/edit/order";

    public static final String API_SERVICE_ORDER_COMPLETED = BuildConfig.BASE_URL
            + "api/mark/ordercomplete";

    public static final String API_RECEIPT_GET = BuildConfig.BASE_URL
            + "api/read/receipt";

    public static final String API_USER_GET = BuildConfig.BASE_URL
            + "api/read/user";

    public static final String API_VERIFY_USER = BuildConfig.BASE_URL
            + "api/verify/user";

    public static final String API_VERIFY_TRUE = BuildConfig.BASE_URL
            + "api/verify/true";

    public static final String API_PRODUCT_PER_CATEGORY_GET = BuildConfig.BASE_URL
            + "api/appyproduct/get/product/percategory";

    public static final String API_PRODUCT_SUB_CATEGORY_GET = BuildConfig.BASE_URL
            + "api/appyproduct/get/subcategoryproducts";

    public static final String API_BANNERS_GET = BuildConfig.BASE_URL
            + "api/get/banners";

    public static final String API_PRODUCT_SEARCH = BuildConfig.BASE_URL
            + "api/appyproduct/get/product/pername";

    public static final String API_PRODUCT_SHIPPING_GET = BuildConfig.BASE_URL
            + "api/appyproduct/get/shipping";

    public static final String API_PRODUCT_SELLER_GET = BuildConfig.BASE_URL
            + "api/appyproduct/get/seller";


    public static final String API_PRODUCT_VARIANT_GET = BuildConfig.BASE_URL
            + "api/appyproduct/get/variants/perproduct";

    public static final String API_PRODUCT_CART_ADD = BuildConfig.BASE_URL
            + "api/appyproduct/cart/add";

    public static final String API_PRODUCT_CART_GET = BuildConfig.BASE_URL
            + "api/appyproduct/cart/get";

    public static final String API_PRODUCT_CART_EDIT = BuildConfig.BASE_URL
            + "api/appyproduct/cart/editquantity";

    public static final String API_PRODUCT_CART_VARIANT_EDIT = BuildConfig.BASE_URL
            + "api/appyproduct/cart/editvariant";

    public static final String API_PRODUCT_CART_DELETE = BuildConfig.BASE_URL
            + "api/appyproduct/cart/delete";

    public static final String API_PRODUCT_CART_EMPTY = BuildConfig.BASE_URL
            + "api/appyproduct/cart/empty";

    public static final String API_WISH_LIST_ADD = BuildConfig.BASE_URL
            + "api/appyproduct/wishlist/add";

    public static final String API_WISH_LIST_DELETE = BuildConfig.BASE_URL
            + "api/appyproduct/wishlist/delete";

    public static final String API_WISH_LIST_EMPTY = BuildConfig.BASE_URL
            + "api/appyproduct/wishlist/empty";

    public static final String API_WISH_LIST_GET = BuildConfig.BASE_URL
            + "api/appyproduct/wishlist/get";

    public static final String API_SHIPPING_ADDRESS_GET = BuildConfig.BASE_URL
            + "api/appyproduct/address/get";

    public static final String API_SHIPPING_ADDRESS_ADD = BuildConfig.BASE_URL
            + "api/appyproduct/address/add";

    public static final String API_SHIPPING_ADDRESS_EDIT = BuildConfig.BASE_URL
            + "api/appyproduct/address/edit";

    public static final String API_SHIPPING_ADDRESS_REMOVE = BuildConfig.BASE_URL
            + "api/appyproduct/address/remove";

    public static final String API_SHIPPING_ADDRESS_SET_DEFAULT = BuildConfig.BASE_URL
            + "api/appyproduct/address/setdefault";

    public static final String API_PRODUCT_ORDER_CHECKOUT = BuildConfig.BASE_URL
            + "api/appyproduct/checkout/order";

    public static final String API_PRODUCT_ORDER_VERIFY = BuildConfig.BASE_URL
            + "api/appyproduct/checkout/verify";

    public static final String API_PRODUCT_ORDER_GET_ALL = BuildConfig.BASE_URL
            + "api/appyproduct/order/getall";

    public static final String URL_FORGET_PASSWORD = "http://dev.lara.appycms.com/password/reset";
    public static final String URL_SCHEDULING_FAQ = "file:///android_asset/html/scheduling_faq.html";
    public static final String URL_AIR_CON_TYPE_INFO = "file:///android_asset/html/air_con_type_info.html";
    public static final String URL_AIR_CON_SIZE_INFO = "file:///android_asset/html/air_con_size_info.html";
    public static final String URL_CLEANING_SUPPLIES = "file:///android_asset/html/cleaning_supplies.html";
    public static final String URL_TERMS_CONDITIONS = "http://appyhomeplus.com/terms-conditions";
    public static final String URL_OUR_FAQ = "file:///android_asset/html/our_faq.html";
    public static final String URL_PRIVACY_POLICY = "http://appyhomeplus.com/privacy-policy/";


    public static final String SEED_DATABASE_SERVICES = "seed/services.json";
    public static final String SEED_DATABASE_SERVICE_CATEGORIES = "seed/services_categories.json";
    public static final String SEED_DATABASE_PRODUCT_CATEGORIES = "seed/product_categories.json";
    public static final String SEED_DATABASE_PRODUCT_TOPICS = "seed/product_topics.json";
    public static final String SEED_DATABASE_PRODUCT_SUBS = "seed/product_subs.json";

    private ApiUrlConfig() {
        // This class is not publicly instantiable
    }

}
