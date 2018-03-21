package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductListActivityModule {

    @Provides
    ProductListViewModel provideProductListViewModel(DataManager dataManager,
                                                     SchedulerProvider schedulerProvider) {
        return new ProductListViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ProductAdapter provideProductAdapter() {
        return new ProductAdapter();
    }

    @Provides
    ViewModelProvider.Factory productListViewModelProvider(ProductListViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
