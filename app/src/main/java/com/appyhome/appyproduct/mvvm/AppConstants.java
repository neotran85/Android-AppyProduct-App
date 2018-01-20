package com.appyhome.appyproduct.mvvm;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

public final class AppConstants {

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";
    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;
    public static final String DB_NAME = "mindorks_mvvm.db";
    public static final String PREF_NAME = "mindorks_pref";
    public static final long NULL_INDEX = -1L;
    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";
    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
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
    }

    public static Activity getFirstActivity() {
        return mFirstActivity;
    }
}
