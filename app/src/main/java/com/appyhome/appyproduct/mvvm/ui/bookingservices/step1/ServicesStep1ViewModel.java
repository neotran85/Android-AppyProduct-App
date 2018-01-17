package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ServicesStep1ViewModel extends BaseViewModel<ServicesStep1Navigator> {

    public ServicesStep1ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
