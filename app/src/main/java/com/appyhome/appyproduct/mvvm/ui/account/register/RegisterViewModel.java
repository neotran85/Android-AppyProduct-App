package com.appyhome.appyproduct.mvvm.ui.account.register;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.LoginResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.account.SignUpResponse;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONObject;

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {

    private String mEmail = "";
    private String mPhoneNumber = "";

    public RegisterViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onServerRegisterClick() {
        getNavigator().register();
    }

    public void onPrivacyPolicyClick() {
        getNavigator().openPrivacyPolicy();
    }

    public void onTermsOfUsageClick() {
        getNavigator().openTermsOfUsage();
    }

    public void register(String firstName, String lastName, String email, String phoneNumber, String password) {
        mEmail = email;
        mPhoneNumber = phoneNumber;
        getNavigator().showLoading();
        getCompositeDisposable().add(getDataManager()
                .doUserSignUp(new SignUpRequest(firstName, lastName, email, phoneNumber, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().closeLoading();
                    handleSignUpResponse(response);
                }, throwable -> {
                    getNavigator().closeLoading();
                    getNavigator().handleErrorService(throwable);
                }));
    }

    public void doVerifyUser() {
        getCompositeDisposable().add(getDataManager()
                .verifyUser()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().closeLoading();
                    handleVerifyUser(response);
                }, throwable -> {
                    getNavigator().closeLoading();
                    getNavigator().handleErrorService(throwable);
                }));
    }

    private void handleVerifyUser(JSONObject response) {
        getNavigator().openPhoneNumberVerification();
    }

    public void doUserLogin(String phone, String password) {
        getNavigator().showLoading();
        getCompositeDisposable().add(getDataManager()
                .doUserLogin(new LoginRequest(phone, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    handleLoginResponse(response);
                    getNavigator().closeLoading();
                }, throwable -> {
                    getNavigator().closeLoading();
                    getNavigator().handleErrorService(throwable);
                }));
    }

    private void handleLoginResponse(LoginResponse loginResponse) {
        if (loginResponse != null && !loginResponse.isEmpty()) {
            String statusCode = loginResponse.getStatusCode();
            String message = loginResponse.getMessage();
            if (statusCode.equals(ApiCode.OK_200)) {
                if (message != null && message.length() > 0) {
                    setAccessToken(message);
                    setEmailUser(mEmail);
                    setPhoneNumber(mPhoneNumber);
                    getDataManager().updateApiHeader(message);
                    doVerifyUser();
                    return;
                }
            }
        }
        // Unknown Error.
        getNavigator().showErrorOthers();
    }

    public void setAccessToken(String token) {
        getDataManager().setAccessToken(token);
    }

    public void setPhoneNumber(String phoneNumber) {
        getDataManager().setCurrentPhoneNumber(phoneNumber);
    }

    public void setEmailUser(String email) {
        getDataManager().setCurrentUserEmail(email);
    }

    private void handleSignUpResponse(SignUpResponse response) {
        if (response != null && !response.isEmpty()) {
            String message = response.getMessage();
            if (message.equals(ApiMessage.PHONE_NUMBER_DUPLICATE)) {
                getNavigator().showErrorPhoneDuplicated();
                return;
            }
            if (message.equals(ApiMessage.EMAIL_DUPLICATE)) {
                getNavigator().showErrorEmailDuplicated();
                return;
            }
            if (message.contains(ApiMessage.USER_CREATED)) {
                String[] result = message.split(":");
                if (result.length == 2) {
                    String userId = result[1];
                    getDataManager().setCurrentUserId(userId);
                }
                getNavigator().login();
                return;
            }
        }
        getNavigator().showErrorOthers();
    }

}
