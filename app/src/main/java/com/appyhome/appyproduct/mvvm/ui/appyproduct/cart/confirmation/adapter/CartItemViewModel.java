package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class CartItemViewModel extends ProductCartItemViewModel {
    public ObservableField<String> totalCostOfStore = new ObservableField<>("");
    public CartItemViewModel() {
        super();
    }
}
