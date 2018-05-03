package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;

import io.realm.RealmResults;

public interface ProductDetailVariantNavigator {
    void selectedVariant(ProductVariant variant);
    void showedVariants(RealmResults<ProductVariant> variants);
}
