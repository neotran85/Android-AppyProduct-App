package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.topic.adapter.TopicAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductTopicFragmentModule {

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_product_topic;
    }

    @Provides
    TopicAdapter provideTopicAdapter() {
        return new TopicAdapter();
    }

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
