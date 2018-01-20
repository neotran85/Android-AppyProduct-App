package com.appyhome.appyproduct.mvvm.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public final class SystemUtils {

    private SystemUtils() {

    }
    public static String getDeviceId(Context context) {
        final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId != null) {
            return deviceId;
        } else {
            return android.os.Build.SERIAL;
        }
    }
}
