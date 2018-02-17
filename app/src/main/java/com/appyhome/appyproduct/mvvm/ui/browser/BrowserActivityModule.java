package com.appyhome.appyproduct.mvvm.ui.browser;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class BrowserActivityModule {

    @Provides
    BrowserViewModel provideBrowserViewModel(DataManager dataManager,
                                             SchedulerProvider schedulerProvider) {
        return new BrowserViewModel(dataManager, schedulerProvider);
    }

}
