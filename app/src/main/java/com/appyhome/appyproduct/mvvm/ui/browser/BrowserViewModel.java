package com.appyhome.appyproduct.mvvm.ui.browser;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class BrowserViewModel extends BaseViewModel<BrowserNavigator> {
    public BrowserViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
