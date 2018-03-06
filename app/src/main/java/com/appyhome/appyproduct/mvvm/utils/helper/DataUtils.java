package com.appyhome.appyproduct.mvvm.utils.helper;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;

import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public final class DataUtils {
    public static boolean isTheSameDate(Calendar cal1, Calendar cal2) {
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }

    public static boolean isToday(Calendar date) {
        Calendar today = Calendar.getInstance();
        return isTheSameDate(date, today);
    }

    public static String getStringNotNull(String text) {
        return text != null ? text : "";
    }

    public static boolean isEqualAndNotNull(String text1, String text2) {
        if (text1 == null || text2 == null) return false;
        return text1.equals(text2);
    }

    public static String getStringSafely(Intent object, String key) {
        if (object.hasExtra(key)) {
            String value = object.getStringExtra(key);
            if (value != null)
                return value;
        }
        return "";
    }

    public static float roundNumber(float number, int decimal) {
        float scale = (float) Math.pow(10, decimal);
        number = number * scale;
        long temp = Math.round(number);
        number = temp / scale;
        return number;
    }

    public static String getStringSafely(JSONObject object, String key) {
        if (object.has(key)) {
            try {
                String value = object.getString(key);
                if (value != null)
                    return value;
            } catch (JSONException e) {
                Crashlytics.logException(e);
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }
}
