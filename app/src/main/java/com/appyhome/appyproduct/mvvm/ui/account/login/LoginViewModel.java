package com.appyhome.appyproduct.mvvm.ui.account.login;

import android.util.Log;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    private String mPhoneNumber = "";
    private String mPassword = "";

    private FetchUserInfoViewModel mUserInfoViewModel;

    public LoginViewModel(DataManager dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mUserInfoViewModel = new FetchUserInfoViewModel(dataManager, schedulerProvider);
    }

    @Override
    public void setNavigator(LoginNavigator navigator) {
        super.setNavigator(navigator);
        mUserInfoViewModel.setNavigator(navigator);
    }

    public void login(String phone, String password) {
        mPhoneNumber = phone;
        mPassword = password;
        getNavigator().showLoading();
        getCompositeDisposable().add(getDataManager()
                .doUserLogin(new LoginRequest(phone, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    handleLoginResponse(response);
                }, throwable -> {
                    getNavigator().closeLoading();
                    getNavigator().handleErrorService(throwable);
                }));
    }

    private void updateUserInfo(String phoneNumber, String token) {
        getCompositeDisposable().add(getDataManager().updateUserInfo(phoneNumber, token)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(user -> {
                    //UPDATED USER INFO SUCCESSFULLY
                }, Crashlytics::logException));
    }

    public void fetchUserData() {
        getNavigator().showLoading();
        mUserInfoViewModel.fetchUserData();
    }

    private void handleLoginResponse(LoginResponse response) {
        if (response != null && !response.isEmpty()) {
            String statusCode = response.getStatusCode();
            String message = response.getMessage();
            if (statusCode.equals(ApiCode.OK_200)) {
                if (message != null && message.length() > 0) {
                    Log.i("TOKEN", message);
                    setAccessToken(message);
                    setPhoneNumber(mPhoneNumber);
                    getDataManager().updateApiHeader(message);
                    updateUserInfo(mPhoneNumber, message);
                    fetchUserData();
                    return;
                }
            }
        }
        getNavigator().closeLoading();
        getNavigator().showErrorLogin();
    }

    public void setAccessToken(String token) {
        getDataManager().setAccessToken(token);
    }

    public void setPhoneNumber(String phoneNumber) {
        getDataManager().setCurrentPhoneNumber(phoneNumber);
    }

}
