package com.appyhome.appyproduct.mvvm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class ValidationUtils {

    private ValidationUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isPhoneNumberValid(String number) {
        return number.matches("^(\\+?6?01)[0|1|2|3|4|6|7|8|9]\\-*[0-9]{7,8}$");
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
