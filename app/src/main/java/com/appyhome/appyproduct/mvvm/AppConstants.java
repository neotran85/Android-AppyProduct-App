package com.appyhome.appyproduct.mvvm;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.AppyProductConstants;

public final class AppConstants {
    public static final String DB_NAME = "appy_database.realm";
    public static final String PREF_NAME = "appyhome_pref";
    public static final long DB_VERSION = 0;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private static Activity mFirstActivity;

    private AppConstants() {
        // This utility class is not publicly instantiable
    }

    public static void initiate(Activity firstActivity) {
        mFirstActivity = firstActivity;
        SCREEN_WIDTH = 0;
        SCREEN_HEIGHT = 0;
        WindowManager w = firstActivity.getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            w.getDefaultDisplay().getSize(size);
            SCREEN_WIDTH = size.x;
            SCREEN_HEIGHT = size.y;
        } else {
            Display d = w.getDefaultDisplay();
            SCREEN_WIDTH = d.getWidth();
            SCREEN_HEIGHT = d.getHeight();
        }
        AppyProductConstants.initiate();
    }

    public static Activity getFirstActivity() {
        return mFirstActivity;
    }
}
