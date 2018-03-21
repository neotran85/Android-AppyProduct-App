package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class FilterFragmentModule {

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_product_filter;
    }

    @Provides
    FilterViewModel provideFilterViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider) {
        return new FilterViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory filterViewModelProvider(FilterViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}
