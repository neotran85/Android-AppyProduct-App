package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;

public class CartItemViewModel extends ProductCartItemViewModel {
    public ObservableField<Float> totalCostOfStore = new ObservableField<>(0.0f);

    public CartItemViewModel() {
        super();
    }
}
