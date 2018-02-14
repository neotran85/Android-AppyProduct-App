package com.appyhome.appyproduct.mvvm.ui.register.verify;

public interface VerifyNavigator {

    void doAfterRegisterSucceeded();

    void handleErrorService(Throwable throwable);

    void showErrorServer();

    void showErrorOthers();

    void showSuccessLogin();

    void showCodeSentMessage();

}
