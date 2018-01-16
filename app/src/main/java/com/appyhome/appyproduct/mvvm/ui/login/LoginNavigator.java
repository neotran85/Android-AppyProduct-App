package com.appyhome.appyproduct.mvvm.ui.login;

import com.appyhome.appyproduct.mvvm.data.model.api.LoginResponse;

public interface LoginNavigator {

    void openMainActivity();

    void handleError(Throwable throwable);

    void handleLoginResponse(LoginResponse response);

    void login();

    void openSignUpActivity();

}
