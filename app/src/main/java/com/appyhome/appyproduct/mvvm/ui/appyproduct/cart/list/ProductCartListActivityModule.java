package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductCartListActivityModule {

    @Provides
    ProductCartListViewModel provideProductCartListViewModel(DataManager dataManager,
                                                             SchedulerProvider schedulerProvider) {
        return new ProductCartListViewModel(dataManager, schedulerProvider);
    }
    @Provides
    ProductCartAdapter provideProductCartAdapter() {
        return new ProductCartAdapter();
    }

}
