package com.appyhome.appyproduct.mvvm.ui.bookingservices.step2;

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

}
