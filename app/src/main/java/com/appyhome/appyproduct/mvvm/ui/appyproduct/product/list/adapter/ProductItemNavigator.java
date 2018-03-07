package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListViewModel;

public interface ProductItemNavigator {
    ProductListViewModel getMainViewModel();
    void updateCartCount();
    void showAlert(String message);
    void notifyItemChanged(int position);
}
