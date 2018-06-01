package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;

public interface ProductCartItemNavigator {

    void backToHomeScreen();

    void showProductDetail(ProductCartItemViewModel viewModel);

    void showProductDetail(ProductItemViewModel viewModel);

    void showAlert(String message);

    void editVariant(ProductCartItemViewModel viewModel);
}
