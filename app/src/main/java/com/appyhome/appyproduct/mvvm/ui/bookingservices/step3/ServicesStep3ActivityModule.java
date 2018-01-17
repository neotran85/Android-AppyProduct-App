package com.appyhome.appyproduct.mvvm.ui.bookingservices.step3;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesStep3ActivityModule {

    @Provides
    ServicesStep3ViewModel provideServicesStep3ViewModel(DataManager dataManager,
                                                         SchedulerProvider schedulerProvider) {
        return new ServicesStep3ViewModel(dataManager, schedulerProvider);
    }

}
