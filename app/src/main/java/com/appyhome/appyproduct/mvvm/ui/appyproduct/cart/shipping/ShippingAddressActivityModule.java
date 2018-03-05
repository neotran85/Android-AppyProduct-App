package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ShippingAddressActivityModule {

    @Provides
    ShippingAddressViewModel provideShippingAddressViewModel(DataManager dataManager,
                                                             SchedulerProvider schedulerProvider) {
        return new ShippingAddressViewModel(dataManager, schedulerProvider);
    }

    @Provides
    AddressAdapter provideAddressAdapter() {
        return new AddressAdapter();
    }
}
