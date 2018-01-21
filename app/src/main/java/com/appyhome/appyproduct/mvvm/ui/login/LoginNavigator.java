package com.appyhome.appyproduct.mvvm.ui.login;

public interface LoginNavigator {
    void openMainActivity();

    void handleErrorService(Throwable throwable);

    void login();

    void openSignUpActivity();

    void showErrorServer();

    void showErrorOthers();

    void showSuccessLogin();
}
