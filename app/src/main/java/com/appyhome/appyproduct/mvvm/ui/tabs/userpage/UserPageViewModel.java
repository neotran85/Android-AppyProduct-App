package com.appyhome.appyproduct.mvvm.ui.tabs.userpage;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class

UserPageViewModel extends BaseViewModel<UserPageNavigator> {

    public ObservableField<String> fullName = new ObservableField<>("");

    public UserPageViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public void updateAccountInfo() {
        if (getDataManager().isUserLoggedIn()) {
            String fullNameStr = getDataManager().getUserFirstName() + " " + getDataManager().getUserLastName();
            fullName.set("Hi, " + fullNameStr);
        } else fullName.set("");
    }

    public void logout() {
        getDataManager().logout();
        if (getNavigator() != null)
            getNavigator().backToHomeScreen();
    }

}
