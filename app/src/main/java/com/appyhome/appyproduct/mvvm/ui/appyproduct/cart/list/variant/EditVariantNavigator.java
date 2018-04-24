package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;

public interface EditVariantNavigator {
    void closeEditVariantFragment();

    void saveProductCartItem_Done(ProductCart productCart);

    void onConfirmationChanged(EditVariantViewModel viewModel);

    void onEditVariantSelected(ProductVariant variant);
}
