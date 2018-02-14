package com.appyhome.appyproduct.mvvm.utils.helper;

public final class StringUtils {
    public static String getStringNotNull(String text) {
        return text != null ? text : "";
    }
    public static boolean isEqualAndNotNull(String text1, String text2) {
        if(text1 == null || text2 == null) return  false;
        return text1.equals(text2);
    }
}
