package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;

public interface OrderCompleteNavigator extends FetchUserInfoNavigator {

    void showAlert(String message);

    void returnHomeScreen();

    void viewOrders();
}
