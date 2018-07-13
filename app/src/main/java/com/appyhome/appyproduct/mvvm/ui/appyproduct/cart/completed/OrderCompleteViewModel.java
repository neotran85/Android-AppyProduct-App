package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class OrderCompleteViewModel extends BaseViewModel<OrderCompleteNavigator> {

    public ObservableField<String> orderPref = new ObservableField<>("");
    private FetchUserInfoViewModel mFetchUserInfoViewModel;

    public OrderCompleteViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mFetchUserInfoViewModel = new FetchUserInfoViewModel(dataManager, schedulerProvider);
    }

    public void setNavigator(OrderCompleteNavigator navigator) {
        super.setNavigator(navigator);
        mFetchUserInfoViewModel.setNavigator(navigator);
    }

    public void updateUserCartAgain() {
        mFetchUserInfoViewModel.fetchAndSyncCartsServer();
    }

}
