package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;

import io.realm.RealmResults;

public interface ProductListNavigator {
    void handleErrorService(Throwable throwable);

    void showAlert(String message);

    void showProducts(RealmResults<Product> result);

    void showProducts(Product[] list);

    void showEmptyProducts();

    void openProductCart();
}
