package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import com.appyhome.appyproduct.mvvm.R;
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
    int provideLayoutId() {
        return R.layout.activity_product_list;
    }
}
