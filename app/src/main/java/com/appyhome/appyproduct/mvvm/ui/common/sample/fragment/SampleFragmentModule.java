package com.appyhome.appyproduct.mvvm.ui.common.sample.fragment;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SampleFragmentModule {

    @Provides
    SampleViewModel provideSampleViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider) {
        return new SampleViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory sampleViewModelProvider(SampleViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_sample;
    }

}
