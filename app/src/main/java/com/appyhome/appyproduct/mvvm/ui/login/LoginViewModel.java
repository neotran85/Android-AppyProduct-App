package com.appyhome.appyproduct.mvvm.ui.login;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.CommonUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManager dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void login(String email, String password) {
        setIsLoading(true);
        getNavigator().openMainActivity();

        /*
        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        getDataManager().updateUserInfo(
                                response.getAccessToken(),
                                response.getUserId(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                response.getUserName(),
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
                */
    }

    public boolean isPhoneNumberAndPasswordValid(String phoneNumber, String password) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        if (!CommonUtils.isPhoneNumberValid(phoneNumber)) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            return false;
        }
        return true;
    }

}
