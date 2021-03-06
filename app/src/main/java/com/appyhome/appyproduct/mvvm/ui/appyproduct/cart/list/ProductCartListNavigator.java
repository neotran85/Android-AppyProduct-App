package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;

import io.realm.RealmResults;

public interface ProductCartListNavigator extends VerifyOrderNavigator, FetchUserInfoNavigator {
    void handleErrorService(Throwable throwable);

    void showAlert(String message);

    void showCarts(RealmResults<ProductCart> result);

    void gotoNextStep();

    void goBack();

    void clearCarts();

    void selectAllCarts();

    void emptyProductCarts();

    void showLoading();

    void closeLoading();

}
