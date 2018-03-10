package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductGalleryActivityModule {

    @Provides
    ProductGalleryViewModel provideSampleViewModel(DataManager dataManager,
                                                   SchedulerProvider schedulerProvider) {
        return new ProductGalleryViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_product_gallery;
    }
}
