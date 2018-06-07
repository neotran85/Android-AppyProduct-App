package com.appyhome.appyproduct.mvvm.ui.tabs.tracking;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class TrackingViewModel extends BaseViewModel<TrackingNavigator> {

    public TrackingViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
