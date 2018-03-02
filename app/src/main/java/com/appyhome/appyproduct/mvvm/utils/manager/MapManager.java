package com.appyhome.appyproduct.mvvm.utils.manager;

import android.app.Activity;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MapManager {
    public static final int PLACE_PICKER_REQUEST = 9999;

    private static String MAP_API_KEY = "AIzaSyD1Z3HWMLNow8ZRmRmgO_pXFn5sw8w5_Ls";

    public static void openMapForPlaceSelection(Activity current) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            current.startActivityForResult(builder.build(current), PLACE_PICKER_REQUEST);
        } catch (Exception e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
    }
}
