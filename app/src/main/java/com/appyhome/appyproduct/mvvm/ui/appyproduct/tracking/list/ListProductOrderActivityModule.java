package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ListProductOrderActivityModule {

    @Provides
    ListProductOrderViewModel provideSampleViewModel(DataManager dataManager,
                                                     SchedulerProvider schedulerProvider) {
        return new ListProductOrderViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_sample;
    }
}
