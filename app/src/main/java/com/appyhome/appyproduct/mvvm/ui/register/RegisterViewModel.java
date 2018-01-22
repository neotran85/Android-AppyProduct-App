package com.appyhome.appyproduct.mvvm.ui.register;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpResponse;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {

    public RegisterViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerRegisterClick() {
        getNavigator().register();
    }

    public void register(String firstName, String lastName, String email, String phoneNumber, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doUserSignUp(new SignUpRequest(firstName, lastName, email, phoneNumber, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SignUpResponse>() {
                    @Override
                    public void accept(SignUpResponse response) throws Exception {
                        setIsLoading(false);
                        handleSignUpResponse(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
                    }
                }));
    }

    public void doUserLogin(String phone, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doUserLogin(new LoginRequest.ServerLoginRequest(phone, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        handleLoginResponse(response);
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
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

    private void handleSignUpResponse(SignUpResponse response) {
        if (response == null || response.getStatusCode() == null
                || response.getStatusCode().length() <= 0
                || response.getMessage() == null
                || response.getMessage().length() <= 0) {
            getNavigator().showErrorServer();
            return;
        }

        String statusCode = response.getStatusCode();
        String message = response.getMessage();
        if (message.equals("phone_number_duplicate")) {
            getNavigator().showErrorPhoneDuplicated();
            return;
        }
        if (statusCode.equals("200")) {
            if (message.contains("user_created")) {
                String[] result = message.split(":");
                if (result != null && result.length == 2) {
                    String userId = result[1];
                    getDataManager().setCurrentUserId(userId);
                    getNavigator().login();
                    return;
                }
            }
            getNavigator().showErrorOthers();
            return;
        }
    }

}
