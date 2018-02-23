package com.appyhome.appyproduct.mvvm.ui.account.myprofile.textinput;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class TextInputDialogModule {

    @Provides
    TextInputViewModel provideTextInputViewModel(DataManager dataManager,
                                                 SchedulerProvider schedulerProvider) {
        return new TextInputViewModel(dataManager, schedulerProvider);
    }

}
