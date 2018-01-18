package com.appyhome.appyproduct.mvvm.ui.custom.detail;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class TextDetailViewModel extends BaseViewModel<TextDetailActivity> {

    public TextDetailViewModel(DataManager dataManager,
                               SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
