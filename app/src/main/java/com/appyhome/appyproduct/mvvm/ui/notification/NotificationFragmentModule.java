package com.appyhome.appyproduct.mvvm.ui.notification;

import android.arch.lifecycle.ViewModelProvider;

import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationFragmentModule {

    @Provides
    NotificationViewModel provideNotificationViewModel(DataManager dataManager,
                                                    SchedulerProvider schedulerProvider) {
        return new NotificationViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory notificationViewModelProvider(NotificationViewModel notificationViewModel) {
        return new ViewModelProviderFactory<>(notificationViewModel);
    }

}
