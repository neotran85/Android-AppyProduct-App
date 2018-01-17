package com.appyhome.appyproduct.mvvm.ui.bookingservices.step5;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesStep5ActivityModule {

    @Provides
    ServicesStep5ViewModel provideServicesStep5ViewModel(DataManager dataManager,
                                                         SchedulerProvider schedulerProvider) {
        return new ServicesStep5ViewModel(dataManager, schedulerProvider);
    }

}
