package com.appyhome.appyproduct.mvvm.utils.helper;

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
}
