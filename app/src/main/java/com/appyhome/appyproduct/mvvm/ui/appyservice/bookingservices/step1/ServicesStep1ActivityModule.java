package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step1;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesStep1ActivityModule {

    @Provides
    ServicesStep1ViewModel provideServicesStep1ViewModel(DataManager dataManager,
                                                         SchedulerProvider schedulerProvider) {
        return new ServicesStep1ViewModel(dataManager, schedulerProvider);
    }


    @Provides
    int provideLayoutId() {
        return R.layout.activity_services_booking_step1;
    }
}
