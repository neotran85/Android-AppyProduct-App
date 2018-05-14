package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;

public interface EditVariantNavigator {
    void closeEditVariantFragment();

    void saveProductCartItem_Done(ProductCart productCart);

    void onEditVariantSelected(ProductVariant variant);
}
