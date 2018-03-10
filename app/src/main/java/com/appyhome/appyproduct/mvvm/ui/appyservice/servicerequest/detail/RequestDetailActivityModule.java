package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.detail;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestItemViewModel;
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


    @Provides
    int provideLayoutId() {
        return R.layout.activity_request_detail;
    }

}
