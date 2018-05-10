package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderNavigator;

public interface PaymentNavigator extends VerifyOrderNavigator{
    void showAlert(String message);

    void gotoNextStep();

    void close();

    void setDefaultPaymentMethod(View view);

}
