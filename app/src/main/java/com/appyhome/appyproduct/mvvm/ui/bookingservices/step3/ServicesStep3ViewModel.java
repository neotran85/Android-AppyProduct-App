package com.appyhome.appyproduct.mvvm.ui.bookingservices.step3;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ServicesStep3ViewModel extends BaseViewModel<ServicesStep3Navigator> {

    public ServicesStep3ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
