package com.appyhome.appyproduct.mvvm.ui.notification;


public interface NotificationNavigator {

    void goBack();

    void handleError(Throwable throwable);
}
