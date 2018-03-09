package com.appyhome.appyproduct.mvvm.ui.common.custom.detail;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class TextDetailActivityModule {

    @Provides
    TextDetailViewModel provideTextDetailViewModel(DataManager dataManager,
                                                   SchedulerProvider schedulerProvider) {
        return new TextDetailViewModel(dataManager, schedulerProvider);
    }

    @Provides
    int provideLayoutId() {
        return R.layout.activity_detail_text;
    }
}
