package com.appyhome.appyproduct.mvvm.ui.common.sample.activity;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SampleActivityModule {

    @Provides
    SampleViewModel provideMainViewModel(DataManager dataManager,
                                         SchedulerProvider schedulerProvider) {
        return new SampleViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory mainViewModelProvider(SampleViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }

}
