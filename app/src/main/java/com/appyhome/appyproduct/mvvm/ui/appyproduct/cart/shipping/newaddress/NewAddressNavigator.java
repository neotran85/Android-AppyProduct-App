package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress;

public interface NewAddressNavigator {
    void showAlert(String message);

    void saveAddress();

    void openMapForPlaceSelection();

    void close();
}
