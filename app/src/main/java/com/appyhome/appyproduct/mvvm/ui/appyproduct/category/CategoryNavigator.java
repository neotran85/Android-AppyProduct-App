package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;

import io.realm.RealmResults;

public interface CategoryNavigator {
    void handleErrorService(Throwable throwable);

    void showAlert(String message);

    void showCategories(RealmResults<ProductCategory> result);

    void showSubCategories(RealmResults<ProductSub> result);

    void openProductCart();

}
