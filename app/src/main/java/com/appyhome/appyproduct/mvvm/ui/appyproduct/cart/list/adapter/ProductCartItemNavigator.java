package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

public interface ProductCartItemNavigator {

    void backToHomeScreen();

    void showProductDetail(ProductCartItemViewModel viewModel);

    void showAlert(String message);

    void editVariant(ProductCartItemViewModel viewModel);
}
