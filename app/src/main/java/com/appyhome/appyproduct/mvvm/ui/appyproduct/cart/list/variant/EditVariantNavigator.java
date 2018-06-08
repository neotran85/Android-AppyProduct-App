package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;

public interface EditVariantNavigator {
    void closeEditVariantFragment();

    void saveProductCartItem_Done();

    void onEditVariantSelected(ProductVariant variant);
}
