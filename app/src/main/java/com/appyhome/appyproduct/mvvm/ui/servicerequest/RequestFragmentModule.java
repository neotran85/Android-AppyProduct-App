package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestFragmentModule {

    @Provides
    RequestViewModel requestViewModel(DataManager dataManager,
                                      SchedulerProvider schedulerProvider) {
        return new RequestViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory provideRequestViewModel(RequestViewModel requestViewModel) {
        return new ViewModelProviderFactory<>(requestViewModel);
    }

}
