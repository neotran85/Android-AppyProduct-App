package com.appyhome.appyproduct.mvvm.ui.appyproduct;

import android.content.Context;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.R;

public final class AppyProductConstants {
    public static class RESOURCE_URL {
        public static final String PRODUCT_DETAIL_PROMOTION_URL = "images/product/promotion/promotionbanner.jpg";
        public static final String PRODUCT_CATEGORY_ALL_SUB_URL = "images/product/sub/all_sub.png";
    }

    public static class LAYOUT_SIZE {
        public static float PRODUCT_SMALLEST_SIZE = 0.0f;
        public static int PRODUCT_DEFAULT_SPAN_COUNT = 2;
        public static int PRODUCT_SPAN_COUNT = 2;
        public static boolean PRODUCT_SMALL_ITEM_MODE = false;
    }

    public static void initiate(Context context) {
        LAYOUT_SIZE.PRODUCT_SMALLEST_SIZE = AppConstants.SCREEN_WIDTH / 2 -
                6 * context.getResources().getDimensionPixelSize(R.dimen.padding_product_in_list);
        int widthScreen = AppConstants.SCREEN_WIDTH;
        int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_product_in_list);
        int widthItem = context.getResources().getDimensionPixelSize(R.dimen.width_thumbnail_product_in_list) + 2 * padding;
        int value = widthScreen / widthItem;
        LAYOUT_SIZE.PRODUCT_SMALL_ITEM_MODE = (value <= LAYOUT_SIZE.PRODUCT_DEFAULT_SPAN_COUNT);
        LAYOUT_SIZE.PRODUCT_SPAN_COUNT = LAYOUT_SIZE.PRODUCT_SMALL_ITEM_MODE ? LAYOUT_SIZE.PRODUCT_DEFAULT_SPAN_COUNT : value;
    }
}
