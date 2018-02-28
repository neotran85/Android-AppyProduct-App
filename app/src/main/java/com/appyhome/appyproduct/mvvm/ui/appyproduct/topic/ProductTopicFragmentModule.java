package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductTopicFragmentModule {

    @Provides
    ProductTopicViewModel provideProductTopicViewModel(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new ProductTopicViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory productTopicViewModelProvider(ProductTopicViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}
