package com.appyhome.appyproduct.mvvm.ui.account.login;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;

public interface LoginNavigator extends FetchUserInfoNavigator {

    void handleErrorService(Throwable throwable);

    void login();

    void openSignUpActivity();

    void showErrorLogin();

    void showErrorServer();

    void showAlert(String message);

    void closeLoading();

    void showLoading();

    void openForgetPassword();
}
