package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.RequestResponse;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

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

    @Provides
    LinearLayoutManager provideLinearLayoutManager(RequestFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
