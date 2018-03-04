package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;

import io.realm.RealmResults;

public interface ShippingAddressNavigator {
    void showAlert(String message);

    void showAddressList(RealmResults<Address> addresses);

    void gotoNextStep();

    void openNewShippingAddress();
}
