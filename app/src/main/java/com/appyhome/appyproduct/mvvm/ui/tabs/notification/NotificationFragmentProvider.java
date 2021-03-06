package com.appyhome.appyproduct.mvvm.ui.tabs.notification;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NotificationFragmentProvider {

    @ContributesAndroidInjector(modules = NotificationFragmentModule.class)
    abstract NotificationFragment provideNotificationFragmentFactory();

}
