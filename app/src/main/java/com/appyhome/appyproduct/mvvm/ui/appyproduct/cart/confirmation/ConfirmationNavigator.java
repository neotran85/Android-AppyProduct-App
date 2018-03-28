package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;

import io.realm.RealmResults;

public interface ConfirmationNavigator {
    void showAlert(String message);

    void gotoNextStep();

    void showCheckedItems(RealmResults<ProductCart> result);

    void editShippingAddress();

    void editPaymentMethod();

    void editCart();

    void handleErrors(Throwable throwable);

    void addOrderOk(ProductOrder order);

    void close();

}
