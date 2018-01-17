package com.appyhome.appyproduct.mvvm.ui.bookingservices.step5;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ServicesStep5ViewModel extends BaseViewModel<ServicesStep5Navigator> {

    public ServicesStep5ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
