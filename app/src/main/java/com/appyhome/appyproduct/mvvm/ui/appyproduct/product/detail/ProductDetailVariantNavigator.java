package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;

public interface ProductDetailVariantNavigator {
    void selectedVariant(ProductVariant variant);
}
