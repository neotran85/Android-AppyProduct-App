package com.appyhome.appyproduct.mvvm.ui.common.sample.activity;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SampleActivityModule {

    @Provides
    SampleViewModel provideSampleViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider) {
        return new SampleViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_sample;
    }
}
