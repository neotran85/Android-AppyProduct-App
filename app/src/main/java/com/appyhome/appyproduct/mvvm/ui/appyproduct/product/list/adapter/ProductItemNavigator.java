package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

public interface ProductItemNavigator {

    BaseViewModel getMainViewModel();

    void updateCartCount();

    void showAlert(String message);

    void notifyFavoriteChanged(int position, boolean isFavorite);

    void onItemClick(ProductItemViewModel viewModel);

    void onFavoriteClick(ProductItemViewModel viewModel);

    void addToCart(ProductItemViewModel viewModel);

    void addedToCartCompleted(int amount, boolean isBuyNow);
}
