package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentActivityModule {

    @Provides
    PaymentViewModel providePaymentViewModel(DataManager dataManager,
                                             SchedulerProvider schedulerProvider) {
        return new PaymentViewModel(dataManager, schedulerProvider);
    }


    @Provides
    int provideLayoutId() {
        return R.layout.activity_product_cart_payment;
    }

}
