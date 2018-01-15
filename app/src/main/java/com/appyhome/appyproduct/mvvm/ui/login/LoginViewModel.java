package com.appyhome.appyproduct.mvvm.ui.login;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginResponse;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.ValidationUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManager dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void login(String phone, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doUserLogin(new LoginRequest.ServerLoginRequest(phone, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        getDataManager().updateUserInfo(
                                response.getAccessToken(),
                                DataManager.LoggedInMode.LOGGED_IN,
                                response.getUserName(),
                                response.getPhoneNumber(),
                                response.getUserEmail(),
                                response.getGoogleProfilePicUrl());
                        setIsLoading(false);
                        getNavigator().openMainActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }
                }));

    }

    public boolean validateData(String phoneNumber, String password) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        if (password == null || password.isEmpty()) {
            return false;
        }

        if (!ValidationUtils.isPhoneNumberValid(phoneNumber)) {
            return false;
        }

        return true;
    }

}
