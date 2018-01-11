package com.appyhome.appyproduct.mvvm.ui.register;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterActivityModule {

    @Provides
    RegisterViewModel provideRegisterViewModel(DataManager dataManager,
                                            SchedulerProvider schedulerProvider) {
        return new RegisterViewModel(dataManager, schedulerProvider);
    }

}
