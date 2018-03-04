package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfirmationActivityModule {

    @Provides
    ConfirmationViewModel provideConfirmationViewModel(DataManager dataManager,
                                                 SchedulerProvider schedulerProvider) {
        return new ConfirmationViewModel(dataManager, schedulerProvider);
    }

}
