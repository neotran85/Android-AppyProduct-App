package com.appyhome.appyproduct.mvvm.utils.helper;

public final class ValidationUtils {

    private ValidationUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isPhoneNumberValid(String number) {
        return number.matches("^(\\+?6?01)[0|1|2|3|4|6|7|8|9]\\-*[0-9]{7,8}$");
    }

    public static boolean isEmailValid(String email) {
        final String reg = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(reg);
    }

    public static boolean isPasswordValid(String password) {
        return (password != null && password.length() >= 6);
    }
}
