package com.appyhome.appyproduct.mvvm.ui.login;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.LoginResponse;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
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
                        setIsLoading(false);
                        handleLoginResponse(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }
                }));
    }

    private void handleLoginResponse(LoginResponse response) {
        if (response == null || response.getStatusCode() == null
                || response.getStatusCode().length() <= 0
                || response.getMessage() == null
                || response.getMessage().length() <= 0) {
            getNavigator().showErrorServer();
            return;
        }
        String statusCode = response.getStatusCode();
        String message = response.getMessage();
        if (statusCode.equals("200")) {
            if (message != null && message.length() > 0) {
                setAccessToken(message);
                getDataManager().updateApiHeader(message);
                getNavigator().showSuccessLogin();
                getNavigator().openMainActivity();
            }
        } else {
            getNavigator().showErrorOthers();
        }
    }

    public void setAccessToken(String token) {
        getDataManager().setAccessToken(token);
    }
}
