package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;

public interface PaymentNavigator extends VerifyOrderNavigator, FetchUserInfoNavigator{
    void showAlert(String message);

    void gotoNextStep();

    void close();

    void setDefaultPaymentMethod(View view);

}
