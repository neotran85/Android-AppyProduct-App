package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.view.View;

public interface ProductCartItemNavigator {
    void showContent(ProductCartAdapter adapter, View view, int idProduct);
    void updateTotalCost(float cost);
}
