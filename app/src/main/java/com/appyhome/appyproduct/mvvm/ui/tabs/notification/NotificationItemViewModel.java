package com.appyhome.appyproduct.mvvm.ui.tabs.notification;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.model.api.NotificationResponse;

public class NotificationItemViewModel {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> content = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();

    private NotificationResponse.Notification mNotification;

    public NotificationItemViewModel(NotificationResponse.Notification notification) {
        mNotification = notification;
        this.title = new ObservableField<>(mNotification.getTitle());
        this.content = new ObservableField<>(mNotification.getDescription());
        this.date = new ObservableField<>(mNotification.getDate());
    }

}
