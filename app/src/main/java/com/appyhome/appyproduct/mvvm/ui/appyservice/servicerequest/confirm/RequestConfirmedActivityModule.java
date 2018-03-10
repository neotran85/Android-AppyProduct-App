package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.confirm;

import com.appyhome.appyproduct.mvvm.R;
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

    @Provides
    int provideLayoutId() {
        return R.layout.activity_request_confirm_completed;
    }
}
