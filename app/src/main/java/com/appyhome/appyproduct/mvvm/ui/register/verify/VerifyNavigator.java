package com.appyhome.appyproduct.mvvm.ui.register.verify;

public interface VerifyNavigator {

    void doAfterRegisterSucceeded();

    void handleErrorService(Throwable throwable);

    void register();

    void login();

    void showErrorServer();

    void showErrorOthers();

    void showSuccessLogin();

    void showErrorPhoneDuplicated();

    void showErrorEmailDuplicated();

    void openTermsOfUsage();

    void openPrivacyPolicy();

}
