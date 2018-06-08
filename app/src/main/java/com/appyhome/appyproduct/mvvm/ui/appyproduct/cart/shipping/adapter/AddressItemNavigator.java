package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter;

public interface AddressItemNavigator {
    void updateDatabaseCompleted();

    void onItemChecked(AddressItemViewModel item);

    void removeAddress(AddressItemViewModel item);

    void editAddress(AddressItemViewModel item);
}
