package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant.EditVariantViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant.ProductVariantViewModel;
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

    @Provides
    ViewModelProvider.Factory productEditVariantViewModelProvider(EditVariantViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
