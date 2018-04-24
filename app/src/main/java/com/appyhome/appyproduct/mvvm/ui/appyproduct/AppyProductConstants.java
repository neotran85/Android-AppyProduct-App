package com.appyhome.appyproduct.mvvm.ui.appyproduct;

import android.content.Context;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.R;

public final class AppyProductConstants {
    public static void initiate(Context context) {
        int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_product_in_list);
        PRODUCT_CONFIG.PRODUCT_SMALLEST_SIZE = AppConstants.SCREEN_WIDTH / 2 -
                6 * padding;
        int widthScreen = AppConstants.SCREEN_WIDTH;
        int imageSize = context.getResources().getDimensionPixelSize(R.dimen.width_thumbnail_product_in_list);
        int widthItem = imageSize + 2 * padding;
        int value = widthScreen / widthItem;
        boolean usingSmallSize = (value <= PRODUCT_CONFIG.PRODUCT_DEFAULT_SPAN_COUNT);
        PRODUCT_CONFIG.PRODUCT_SIZE = usingSmallSize
                ? PRODUCT_CONFIG.PRODUCT_SMALLEST_SIZE : imageSize;
        PRODUCT_CONFIG.PRODUCT_SPAN_COUNT = usingSmallSize ? PRODUCT_CONFIG.PRODUCT_DEFAULT_SPAN_COUNT : value;
    }

    public static class RESOURCE_URL {
        public static final String PRODUCT_DETAIL_PROMOTION_URL = "images/product/promotion/promotionbanner.jpg";
        public static final String PRODUCT_CATEGORY_ALL_SUB_URL = "images/product/sub/all_sub.png";
    }

    public static class PRODUCT_CONFIG {
        private static final int PRODUCT_DEFAULT_SPAN_COUNT = 2;
        public static float PRODUCT_SIZE = 0.0f;
        public static int PRODUCT_SPAN_COUNT = PRODUCT_DEFAULT_SPAN_COUNT;
        private static float PRODUCT_SMALLEST_SIZE = 0.0f;
    }
}
