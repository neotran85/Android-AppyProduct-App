package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;

public class CartItemViewModel extends ProductCartItemViewModel {
    public ObservableField<String> totalCostOfStore = new ObservableField<>("");

    public CartItemViewModel() {
        super();
    }
}
