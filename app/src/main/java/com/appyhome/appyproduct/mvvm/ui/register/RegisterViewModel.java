package com.appyhome.appyproduct.mvvm.ui.register;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.ValidationUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {

    public RegisterViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerRegisterClick() {
        getNavigator().register();
    }

    public void register(String email, String password) {
        setIsLoading(true);
        getNavigator().openMainActivity();
    }

    public boolean isPhoneNumberAndPasswordValid(String phoneNumber, String password) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        if (!ValidationUtils.isPhoneNumberValid(phoneNumber)) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            return false;
        }
        return true;
    }

}
