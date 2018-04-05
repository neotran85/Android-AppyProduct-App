package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant;


import android.view.View;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;

import io.realm.RealmList;
import io.realm.RealmResults;

public interface ProductVariantNavigator {
    void showProductVariants(RealmResults<ProductVariant> result);
    void selectVariant(View view);
}
