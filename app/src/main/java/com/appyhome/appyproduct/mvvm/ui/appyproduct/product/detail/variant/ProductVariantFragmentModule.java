package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductVariantFragmentModule {

    @Provides
    ProductVariantViewModel provideProductVariantViewModel(DataManager dataManager,
                                                           SchedulerProvider schedulerProvider) {
        return new ProductVariantViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory productVariantViewModelProvider(ProductVariantViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_product_variant;
    }

}
