package com.appyhome.appyproduct.mvvm.ui.appyproduct;

import android.content.Context;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.R;

public final class AppyProductConstants {
    public static void initiate() {
        PRODUCT_CONFIG.PRODUCT_SIZE = AppConstants.SCREEN_WIDTH / 2 ;
    }

    public static class RESOURCE_URL {
        public static final String PRODUCT_DETAIL_PROMOTION_URL = "images/product/promotion/promotionbanner.jpg";
        public static final String PRODUCT_CATEGORY_ALL_SUB_URL = "images/product/sub/all_sub.png";
    }

    public static class PRODUCT_CONFIG {
        private static final int PRODUCT_DEFAULT_SPAN_COUNT = 2;
        public static float PRODUCT_SIZE = 0.0f;
        public static int PRODUCT_SPAN_COUNT = PRODUCT_DEFAULT_SPAN_COUNT;
    }
}
