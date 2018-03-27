package com.appyhome.appyproduct.mvvm.ui.account.login;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    private String mPhoneNumber = "";
    private String mPassword = "";
    private int mTryCounter = 0;

    public LoginViewModel(DataManager dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void login(String phone, String password) {
        setIsLoading(true);
        mPhoneNumber = phone;
        mPassword = password;
        mTryCounter = 0;
        getCompositeDisposable().add(getDataManager()
                .doUserLogin(new LoginRequest(phone, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    handleLoginResponse(response);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleErrorService(throwable);
                }));
    }

    private void updateUserInfo(String phoneNumber, String token) {
        getCompositeDisposable().add(getDataManager().updateUserInfo(phoneNumber, token)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(user -> {
                    //UPDATE SUCCESS
                }, Crashlytics::logException));
    }

    private void handleLoginResponse(LoginResponse response) {
        if (response != null && !response.isEmpty()) {
            String statusCode = response.getStatusCode();
            String message = response.getMessage();
            if (statusCode.equals(ApiCode.OK_200)) {
                if (message != null && message.length() > 0) {
                    setAccessToken(message);
                    setPhoneNumber(mPhoneNumber);
                    getDataManager().updateApiHeader(message);
                    getNavigator().showSuccessLogin();
                    getNavigator().doAfterLoginSucceeded();
                    updateUserInfo(mPhoneNumber, message);
                    return;
                }
            } else {
                // Failed
                if (mTryCounter == 0) {
                    login("+" + mPhoneNumber, mPassword);
                } else {
                    // Account is exist
                    if (message != null) {
                        if (message.equals(ApiMessage.INVALID_PHONE_NUMBER))
                            // Account is not exist
                            getNavigator().showSignUpDialog();
                        else
                            // Phone number is invalid
                            getNavigator().showErrorOthers();
                    }
                }
                mTryCounter++;
                return;
            }
        }
        // Unknown Error
        getNavigator().showErrorServer();
    }

    public void setAccessToken(String token) {
        getDataManager().setAccessToken(token);
    }

    public void setPhoneNumber(String phoneNumber) {
        getDataManager().setCurrentPhoneNumber(phoneNumber);
    }
}
