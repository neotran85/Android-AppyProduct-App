package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class CartItemViewModel extends ProductCartItemViewModel {
    public ObservableField<String> totalCostOfStore = new ObservableField<>("");
    public CartItemViewModel() {
        super();
    }
    public CartItemViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
