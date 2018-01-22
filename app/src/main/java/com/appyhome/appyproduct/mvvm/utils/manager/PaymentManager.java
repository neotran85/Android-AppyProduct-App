package com.appyhome.appyproduct.mvvm.utils.manager;


import android.app.Activity;
import android.content.Intent;

import com.molpay.molpayxdk.MOLPayActivity;

import java.util.HashMap;

public class PaymentManager {
    private static PaymentManager mInstance;

    private PaymentManager() {

    }

    public static PaymentManager getInstance() {
        if (mInstance == null) {
            mInstance = new PaymentManager();
        }
        return mInstance;
    }

    public void startPaymentActivity(Activity currentActivity) {
        if (currentActivity != null) {
            HashMap<String, Object> paymentDetails = new HashMap<>();
            // Mandatory String. A value not less than '1.00'
            paymentDetails.put(MOLPayActivity.mp_amount, "1.10");

            // Mandatory String. Values obtained from MOLPay
            paymentDetails.put(MOLPayActivity.mp_username, "username");
            paymentDetails.put(MOLPayActivity.mp_password, "password");
            paymentDetails.put(MOLPayActivity.mp_merchant_ID, "merchantid");
            paymentDetails.put(MOLPayActivity.mp_app_name, "appname");
            paymentDetails.put(MOLPayActivity.mp_verification_key, "vkey123");

            // Mandatory String. Payment values
            paymentDetails.put(MOLPayActivity.mp_order_ID, "orderid123");
            paymentDetails.put(MOLPayActivity.mp_currency, "MYR");
            paymentDetails.put(MOLPayActivity.mp_country, "MY");

            Intent intent = new Intent(currentActivity, MOLPayActivity.class);
            intent.putExtra(MOLPayActivity.MOLPayPaymentDetails, paymentDetails);
            currentActivity.startActivityForResult(intent, MOLPayActivity.MOLPayXDK);
        }
    }
}
