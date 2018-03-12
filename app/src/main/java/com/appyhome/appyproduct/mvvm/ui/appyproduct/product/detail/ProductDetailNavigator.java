package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

public interface ProductDetailNavigator {
    void showAlert(String message);
    void gotoCart();
    void addToCart();
    void updateCartCount();
    void addedToCartAndBuyNow();
}
