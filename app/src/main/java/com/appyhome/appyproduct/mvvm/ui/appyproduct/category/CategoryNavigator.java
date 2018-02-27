package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;

import io.realm.RealmResults;

public interface CategoryNavigator {
    void handleErrorService(Throwable throwable);
    void showErrorServer();
    void showErrorOthers();
    void showAlert(String message);
    void showCategories(RealmResults<ProductCategory> result);
}
