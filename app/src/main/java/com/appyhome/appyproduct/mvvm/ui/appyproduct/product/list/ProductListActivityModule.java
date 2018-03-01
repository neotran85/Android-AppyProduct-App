package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import com.appyhome.appyproduct.mvvm.data.DataManager;
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

}
