package com.appyhome.appyproduct.mvvm.utils.manager;


import android.app.Activity;
import android.content.Intent;

import com.molpay.molpayxdk.BuildConfig;
import com.molpay.molpayxdk.MOLPayActivity;

import java.util.HashMap;

public class PaymentManager {
    private static PaymentManager mInstance;
    private static final String BILL_DESCRIPTION = "Appyhome Service Payment";
    private static final String MERCHANT_ID = "appyhomeplus";
    private static final String USERNAME = "api_appyhomeplus";
    private static final String PASSWORD = "api_appy017home#";
    private static final String APP_NAME = "appyhomeplus";
    private static final String VERIFICATION_KEY = "e67f92a9311b8d7e6a423bf00e8c2168";
    private static final String MULTIPLE = "multi";
    private static final String CURRENCY = "MYR";
    private static final String COUNTRY = "MY";

    private PaymentManager() {

    }

    public static PaymentManager getInstance() {
        if (mInstance == null) {
            mInstance = new PaymentManager();
        }
        return mInstance;
    }

    public void startPaymentActivity(Activity currentActivity, String amountOfPayment, String orderId, String phoneNumber, String email) {
        if (currentActivity != null) {
            HashMap<String, Object> paymentDetails = new HashMap<>();
            // Mandatory String. A value not less than '1.00'
            paymentDetails.put(MOLPayActivity.mp_amount, amountOfPayment);

            // Mandatory String. Values obtained from MOLPay
            paymentDetails.put(MOLPayActivity.mp_merchant_ID, MERCHANT_ID);
            paymentDetails.put(MOLPayActivity.mp_username, USERNAME);
            paymentDetails.put(MOLPayActivity.mp_password, PASSWORD);
            paymentDetails.put(MOLPayActivity.mp_app_name, APP_NAME);
            paymentDetails.put(MOLPayActivity.mp_verification_key, VERIFICATION_KEY);

            // Mandatory String. Payment values
            paymentDetails.put(MOLPayActivity.mp_order_ID, orderId);
            paymentDetails.put(MOLPayActivity.mp_currency, CURRENCY);
            paymentDetails.put(MOLPayActivity.mp_country, COUNTRY);
            paymentDetails.put(MOLPayActivity.mp_bill_mobile, phoneNumber);
            paymentDetails.put(MOLPayActivity.mp_bill_email, email);
            paymentDetails.put(MOLPayActivity.mp_bill_description, BILL_DESCRIPTION);
            paymentDetails.put(MOLPayActivity.mp_channel, MULTIPLE);

            if (BuildConfig.DEBUG) {
                paymentDetails.put(MOLPayActivity.mp_sandbox_mode, true);
                paymentDetails.put(MOLPayActivity.mp_dev_mode, true);
            }

            Intent intent = new Intent(currentActivity, MOLPayActivity.class);
            intent.putExtra(MOLPayActivity.MOLPayPaymentDetails, paymentDetails);
            currentActivity.startActivityForResult(intent, MOLPayActivity.MOLPayXDK);
        }
    }
}
