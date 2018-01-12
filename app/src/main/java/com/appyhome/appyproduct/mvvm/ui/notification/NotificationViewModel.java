package com.appyhome.appyproduct.mvvm.ui.notification;

import android.databinding.ObservableArrayList;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.NotificationResponse;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

public class NotificationViewModel extends BaseViewModel<NotificationNavigator> {
    public String temp;
    private final ObservableArrayList<NotificationResponse.Notification> notificationObservableArrayList = new ObservableArrayList<>();
    public NotificationViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void addNotificationItemsToList(List<NotificationResponse.Notification> list) {
        notificationObservableArrayList.clear();
        notificationObservableArrayList.addAll(list);
    }

    public ObservableArrayList<NotificationResponse.Notification> getNotificationObservableArrayList() {
        return notificationObservableArrayList;
    }
}
