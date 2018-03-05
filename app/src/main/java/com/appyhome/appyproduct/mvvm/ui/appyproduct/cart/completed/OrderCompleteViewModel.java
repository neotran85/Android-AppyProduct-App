package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class OrderCompleteViewModel extends BaseViewModel<OrderCompleteNavigator> {
    public OrderCompleteViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
