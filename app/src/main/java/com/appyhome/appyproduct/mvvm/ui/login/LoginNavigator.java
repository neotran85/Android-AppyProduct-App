package com.appyhome.appyproduct.mvvm.ui.login;

public interface LoginNavigator {

    void openMainActivity();

    void handleError(Throwable throwable);

    void login();

}
