package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.shipping;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter.AddressAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ChooseShippingAddressActivityModule {

    @Provides
    ShippingAddressViewModel provideShippingAddressViewModel(DataManager dataManager,
                                                             SchedulerProvider schedulerProvider) {
        return new ShippingAddressViewModel(dataManager, schedulerProvider);
    }

    @Provides
    AddressAdapter provideAddressAdapter(DataManager dataManager,
                                         SchedulerProvider schedulerProvider) {
        return new AddressAdapter(dataManager, schedulerProvider);
    }

}
