package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.detail;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductTrackingActivityModule {

    @Provides
    ProductTrackingViewModel provideProductTrackingViewModel(DataManager dataManager,
                                                             SchedulerProvider schedulerProvider) {
        return new ProductTrackingViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_product_tracking;
    }
}
