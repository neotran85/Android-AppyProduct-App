package com.appyhome.appyproduct.mvvm.ui.tabs.notification;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.appyhome.appyproduct.mvvm.ViewModelProviderFactory;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.NotificationResponse;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationFragmentModule {

    @Provides
    NotificationViewModel notificationViewModel(DataManager dataManager,
                                                SchedulerProvider schedulerProvider) {
        return new NotificationViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory provideNotificationViewModel(NotificationViewModel notificationViewModel) {
        return new ViewModelProviderFactory<>(notificationViewModel);
    }

    @Provides
    NotificationAdapter provideNotificationAdapter() {
        ArrayList<NotificationItemViewModel> array = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NotificationItemViewModel item = new NotificationItemViewModel(new NotificationResponse.Notification());
            array.add(item);
        }
        return new NotificationAdapter(array);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(NotificationFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
