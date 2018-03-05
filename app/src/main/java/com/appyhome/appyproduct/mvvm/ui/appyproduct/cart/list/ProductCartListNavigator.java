package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;

import io.realm.RealmResults;

public interface ProductCartListNavigator {
    void handleErrorService(Throwable throwable);

    void showAlert(String message);

    void showCart(RealmResults<ProductCart> result);

    void gotoNextStep();

    void goBack();
}
