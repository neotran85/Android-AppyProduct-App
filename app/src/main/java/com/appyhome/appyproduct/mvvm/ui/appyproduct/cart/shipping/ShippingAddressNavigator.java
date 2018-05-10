package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderNavigator;

import io.realm.RealmResults;

public interface ShippingAddressNavigator {
    void showAlert(String message);

    void showAddressList(RealmResults<AppyAddress> addresses);

    void gotoNextStep();

    void openNewShippingAddress();

    void close();
}
