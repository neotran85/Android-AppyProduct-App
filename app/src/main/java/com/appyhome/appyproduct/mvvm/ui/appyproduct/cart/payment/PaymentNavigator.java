package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment;

import android.view.View;

public interface PaymentNavigator {
    void showAlert(String message);

    void gotoNextStep();

    void close();

    void setDefaultPaymentMethod(View view);

}
