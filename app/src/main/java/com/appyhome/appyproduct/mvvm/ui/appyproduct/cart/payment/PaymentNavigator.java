package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment;

public interface PaymentNavigator {
    void showAlert(String message);

    void gotoNextStep();

    void close();
}
