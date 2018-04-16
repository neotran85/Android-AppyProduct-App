package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant.ProductVariantViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductDetailActivityModule {

    public static ProductItemViewModel clickedViewModel;
    public static ProductAdapter relatedProductAdapter;

    @Provides
    ProductItemViewModel provideProductItemViewModel(DataManager dataManager,
                                                     SchedulerProvider schedulerProvider) {
        if (clickedViewModel != null)
            return clickedViewModel;
        else
            return new ProductItemViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory productVariantViewModelProvider(ProductVariantViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    ProductAdapter provideRelatedProductAdapter() {
        return relatedProductAdapter;
    }
}


