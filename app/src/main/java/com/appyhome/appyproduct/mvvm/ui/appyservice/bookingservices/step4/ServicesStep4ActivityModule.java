package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step4;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesStep4ActivityModule {

    @Provides
    ServicesStep4ViewModel provideServicesStep4ViewModel(DataManager dataManager,
                                                         SchedulerProvider schedulerProvider) {
        return new ServicesStep4ViewModel(dataManager, schedulerProvider);
    }

}
