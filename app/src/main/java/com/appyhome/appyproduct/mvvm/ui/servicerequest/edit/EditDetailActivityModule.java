package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class EditDetailActivityModule {

    @Provides
    EditDetailViewModel provideEditDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new EditDetailViewModel(dataManager, schedulerProvider);
    }

}
