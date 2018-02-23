package com.appyhome.appyproduct.mvvm.ui.account.login;

public interface LoginNavigator {
    void doAfterLoginSucceeded();

    void handleErrorService(Throwable throwable);

    void login();

    void openSignUpActivity();

    void showErrorServer();

    void showErrorOthers();

    void showSignUpDialog();

    void showSuccessLogin();
}
