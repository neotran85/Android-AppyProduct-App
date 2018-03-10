package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.edit;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestEditActivityModule {

    @Provides
    RequestEditViewModel provideRequestEditViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new RequestEditViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_request_edit;
    }

}
