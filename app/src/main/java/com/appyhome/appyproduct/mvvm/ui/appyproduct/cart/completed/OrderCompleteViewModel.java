package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class OrderCompleteViewModel extends BaseViewModel<OrderCompleteNavigator> {

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
