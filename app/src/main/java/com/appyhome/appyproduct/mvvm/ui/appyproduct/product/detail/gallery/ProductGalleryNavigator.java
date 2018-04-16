package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;

public interface ProductGalleryNavigator {
    void showAlert(String message);
    void showGallery(ProductVariant variant);
    void close();
}
