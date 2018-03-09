package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.completed;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderCompleteActivityModule {

    @Provides
    OrderCompleteViewModel provideOrderCompletedViewModel(DataManager dataManager,
                                                          SchedulerProvider schedulerProvider) {
        return new OrderCompleteViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_product_cart_completed;
    }

}
