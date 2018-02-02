package com.appyhome.appyproduct.mvvm.ui.main;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class MainViewModel extends BaseViewModel<MainNavigator> {
   public MainViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public boolean isUserLoggedIn() {
        return getDataManager().isUserLoggedIn();
    }
}
