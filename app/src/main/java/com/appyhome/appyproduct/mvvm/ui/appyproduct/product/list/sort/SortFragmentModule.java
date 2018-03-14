package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SortFragmentModule {

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_product_sort;
    }

    @Provides
    SortViewModel provideSortViewModel(DataManager dataManager,
                                       SchedulerProvider schedulerProvider) {
        return new SortViewModel(dataManager, schedulerProvider);
    }

    @Provides
    SortOptionsAdapter provideSortOptionsAdapter() {
        return new SortOptionsAdapter();
    }

    @Provides
    ViewModelProvider.Factory sortViewModelProvider(SortViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

}
