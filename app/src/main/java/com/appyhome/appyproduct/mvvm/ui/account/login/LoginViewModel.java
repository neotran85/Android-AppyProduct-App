package com.appyhome.appyproduct.mvvm.ui.account.login;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

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
                .doUserLogin(new LoginRequest.ServerLoginRequest(phone, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        setIsLoading(false);
                        handleLoginResponse(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
                    }
                }));
    }

    private void getUserByPhoneNumber(String phoneNumber) {
        getNavigator().showAlert("getUserByPhoneNumber");
        getCompositeDisposable().add(getDataManager().getUserByPhoneNumber(phoneNumber)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        getNavigator().showAlert("accept");
                        getNavigator().showAlert(user.getPhoneNumber());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
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
                    //getNavigator().showSuccessLogin();
                    //getNavigator().doAfterLoginSucceeded();
                    getUserByPhoneNumber(mPhoneNumber);
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
