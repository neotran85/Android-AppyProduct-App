package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;

import io.realm.RealmResults;

public interface ProductCartListNavigator {
    void handleErrorService(Throwable throwable);
    void showErrorServer();
    void showErrorOthers();
    void showAlert(String message);
}
