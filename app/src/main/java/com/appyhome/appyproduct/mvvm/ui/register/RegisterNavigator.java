package com.appyhome.appyproduct.mvvm.ui.register;

public interface RegisterNavigator {

    void doAfterRegisterSucceeded();

    void handleErrorService(Throwable throwable);

    void register();

    void login();

    void showErrorServer();

    void showErrorOthers();

    void showSuccessLogin();

    void showErrorPhoneDuplicated();

    void showErrorEmailDuplicated();

}
