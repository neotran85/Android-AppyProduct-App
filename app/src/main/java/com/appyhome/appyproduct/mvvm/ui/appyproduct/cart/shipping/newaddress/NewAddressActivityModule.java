package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class NewAddressActivityModule {

    @Provides
    NewAddressViewModel provideNewAddressViewModel(DataManager dataManager,
                                                   SchedulerProvider schedulerProvider) {
        return new NewAddressViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_product_shipping_new;
    }

}
