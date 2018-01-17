package com.appyhome.appyproduct.mvvm.ui.bookingservices.step2;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ServicesStep2ViewModel extends BaseViewModel<ServicesStep2Navigator> {

    public ServicesStep2ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
