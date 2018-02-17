package com.appyhome.appyproduct.mvvm.utils.helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public final class DataUtils {
    public static boolean isTheSameDate(Calendar cal1,Calendar cal2) {
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
        if(text1 == null || text2 == null) return  false;
        return text1.equals(text2);
    }

    public static String getStringSafely(JSONObject object, String key) {
        if (object.has(key)) {
            try {
                String value = object.getString(key);
                if(value != null)
                    return value;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
