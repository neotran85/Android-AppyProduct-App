package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

import io.realm.RealmResults;

public interface ProductItemNavigator {

    BaseViewModel getMainViewModel();

    void updateCartCount();

    void showAlert(String message);

    void notifyFavoriteChanged(int position, boolean isFavorite);

    void onItemClick(ProductItemViewModel viewModel);

    void onFavoriteClick(ProductItemViewModel viewModel);

    void addToCart(ProductItemViewModel viewModel);

    void addedToCartCompleted(int amount, boolean isBuyNow);

    void showRelatedProducts(RealmResults<Product> products);
}
