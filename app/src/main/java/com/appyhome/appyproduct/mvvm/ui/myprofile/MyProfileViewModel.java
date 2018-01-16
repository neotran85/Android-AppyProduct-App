package com.appyhome.appyproduct.mvvm.ui.myprofile;

import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class MyProfileViewModel extends BaseViewModel<MyProfileNavigator> {

    public MyProfileViewModel(DataManager dataManager,
                              SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onFieldClick(View view) {
        getNavigator().onFieldClick(view);
    }

}
