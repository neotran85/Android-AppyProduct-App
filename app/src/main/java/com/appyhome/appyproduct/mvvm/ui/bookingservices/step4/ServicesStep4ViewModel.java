package com.appyhome.appyproduct.mvvm.ui.bookingservices.step4;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ServicesStep4ViewModel extends BaseViewModel<ServicesStep4Navigator> {

    public ServicesStep4ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
