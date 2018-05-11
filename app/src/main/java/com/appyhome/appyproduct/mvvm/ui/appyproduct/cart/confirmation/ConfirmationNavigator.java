package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

import io.realm.RealmResults;

public interface ConfirmationNavigator extends FetchUserInfoNavigator, VerifyOrderNavigator{
    void showAlert(String message);

    void gotoNextStep();

    void showCheckedItems(RealmResults<ProductCart> result, int addressId);

    void editShippingAddress();

    void editPaymentMethod();

    void editCart();

    void handleErrors(Throwable throwable);

    void addOrderOk(ProductOrder order);

    void close();

    void openVisaPayment();

    ConfirmationViewModel getViewModel();
}
