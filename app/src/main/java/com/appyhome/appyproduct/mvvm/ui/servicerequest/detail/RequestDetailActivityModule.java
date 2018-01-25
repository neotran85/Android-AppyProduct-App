package com.appyhome.appyproduct.mvvm.ui.servicerequest.detail;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestDetailActivityModule {

    @Provides
    RequestDetailViewModel provideRequestDetailViewModel(DataManager dataManager,
                                                   SchedulerProvider schedulerProvider) {
        return new RequestDetailViewModel(dataManager, schedulerProvider);
    }

}
