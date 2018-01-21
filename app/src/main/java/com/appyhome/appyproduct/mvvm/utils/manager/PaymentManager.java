package com.appyhome.appyproduct.mvvm.utils.manager;


public class PaymentManager {
    private static PaymentManager mInstance;
    private PaymentManager() {

    }
    public static PaymentManager getInstance() {
        if(mInstance == null) {
            mInstance = new PaymentManager();
        }
        return mInstance;
    }
}
