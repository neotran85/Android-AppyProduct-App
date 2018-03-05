package com.appyhome.appyproduct.mvvm.ui.tabs.home;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {
    public HomeViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
