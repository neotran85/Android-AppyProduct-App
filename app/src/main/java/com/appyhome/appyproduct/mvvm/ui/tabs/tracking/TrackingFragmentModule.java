package com.appyhome.appyproduct.mvvm.ui.tabs.tracking;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class TrackingFragmentModule {

    @Provides
    TrackingViewModel provideTrackingViewModel(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new TrackingViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory trackingViewModelProvider(TrackingViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_tracking;
    }

}
