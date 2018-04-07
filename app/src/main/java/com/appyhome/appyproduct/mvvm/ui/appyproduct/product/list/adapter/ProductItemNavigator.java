package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

public interface ProductItemNavigator {

    BaseViewModel getMainViewModel();

    void updateCartCount();

    void showAlert(String message);

    void notifyFavoriteChanged(int position, boolean isFavorite);

    void onItemClick(ProductItemViewModel viewModel);

    void onFavoriteClick(ProductItemViewModel viewModel);

    void addedToCartCompleted(boolean isBuyNow);
}
