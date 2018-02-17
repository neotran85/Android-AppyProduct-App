package com.appyhome.appyproduct.mvvm.ui.servicerequest.detail;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestDetailActivityModule {

    @Provides
    RequestItemViewModel provideRequestItemViewModel(DataManager dataManager,
                                                     SchedulerProvider schedulerProvider) {
        return new RequestItemViewModel(dataManager, schedulerProvider);
    }

}
