package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class EditVariantFragmentModule {

    @Provides
    EditVariantViewModel provideEditVariantViewModel(DataManager dataManager,
                                                SchedulerProvider schedulerProvider) {
        return new EditVariantViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory editVariantViewModelProvider(EditVariantViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.fragment_product_variant_edit;
    }

}
