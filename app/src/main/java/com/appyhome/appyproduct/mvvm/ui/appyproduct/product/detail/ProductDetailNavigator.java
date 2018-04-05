package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;

public interface ProductDetailNavigator extends ProductItemNavigator {
    void showAlert(String message);

    void gotoCart();

    void addToCart();

    void updateCartCount();

    void addToFavorite();

    void increaseAmount();

    void decreaseAmount();

    void showGallery(int position);

    void share();

    void selectedVariant(ProductVariant variant);

    void showedVariants();
}
