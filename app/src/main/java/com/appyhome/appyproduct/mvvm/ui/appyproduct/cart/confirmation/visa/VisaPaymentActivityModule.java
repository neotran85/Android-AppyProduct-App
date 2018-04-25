package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.visa;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class VisaPaymentActivityModule {

    @Provides
    VisaPaymentViewModel provideVisaPaymentViewModel(DataManager dataManager,
                                                SchedulerProvider schedulerProvider) {
        return new VisaPaymentViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_visa_payment;
    }
}
