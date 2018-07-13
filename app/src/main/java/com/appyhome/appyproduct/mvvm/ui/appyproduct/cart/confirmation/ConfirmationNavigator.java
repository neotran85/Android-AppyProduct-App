package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;

import io.realm.RealmResults;

public interface ConfirmationNavigator extends FetchUserInfoNavigator, VerifyOrderNavigator {
    void showAlert(String message);

    void gotoNextStep();

    void showCheckedItems(RealmResults<ProductCart> result, int addressId);

    void editShippingAddress();

    void editPaymentMethod();

    void editCart();

    void handleErrors(Throwable throwable);

    void addOrderOk(long orderId);

    void addOrderFailed(String message);

    void close();

    void openVisaPayment();

    ConfirmationViewModel getViewModel();

    void updateTotalShippingCost(double total);
}
