package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductDetailActivityModule {

    public static ProductItemViewModel clickedViewModel;

    @Provides
    ProductItemViewModel provideProductItemViewModel(DataManager dataManager,
                                                  SchedulerProvider schedulerProvider) {
        return clickedViewModel;
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_product_detail;
    }
}
