package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class BookingServicesViewModel extends BaseViewModel<BookingServicesNavigator> {

    public BookingServicesViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
