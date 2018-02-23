package com.appyhome.appyproduct.mvvm.ui.account.register.verify;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class VerifyActivityModule {

    @Provides
    VerifyViewModel provideVerifyViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider) {
        return new VerifyViewModel(dataManager, schedulerProvider);
    }

}
