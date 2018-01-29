package com.appyhome.appyproduct.mvvm.ui.servicerequest.confirm;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestConfirmedActivityModule {

    @Provides
    RequestConfirmedViewModel provideRequestConfirmedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new RequestConfirmedViewModel(dataManager, schedulerProvider);
    }

}
