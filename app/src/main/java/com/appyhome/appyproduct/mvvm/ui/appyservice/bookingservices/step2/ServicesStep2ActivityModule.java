package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step2;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesStep2ActivityModule {

    @Provides
    ServicesStep2ViewModel provideServicesStep2ViewModel(DataManager dataManager,
                                                         SchedulerProvider schedulerProvider) {
        return new ServicesStep2ViewModel(dataManager, schedulerProvider);
    }


    @Provides
    int provideLayoutId() {
        return R.layout.activity_services_booking_step2;
    }
}
