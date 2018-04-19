package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;

public interface ProductDetailNavigator extends ProductItemNavigator {
    void showAlert(String message);

    void gotoCart();

    void addToCart();

    void updateCartCount();

    void share();

    void selectedVariant(ProductVariant variant);

    void onFavoriteClick(ProductItemViewModel viewModel);

    void back();

    void goToDetailSection();

    void goToOverviewSection();

    void goToReviewSection();
}
