package com.appyhome.appyproduct.mvvm.utils.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.appyhome.appyproduct.mvvm.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public final class NetworkUtils {

    private NetworkUtils() {
        // This class is not publicly instantiable
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean value = netInfo != null && netInfo.isConnectedOrConnecting();
        if (!value) {
            StyleableToast.makeText(context, context.getString(R.string.error_network_not_connected),
                    Toast.LENGTH_LONG, R.style.AppyToast_ErrorConnection).show();
        }
        return value;
    }
}
