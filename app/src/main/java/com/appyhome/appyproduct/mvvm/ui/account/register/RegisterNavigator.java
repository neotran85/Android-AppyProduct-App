package com.appyhome.appyproduct.mvvm.ui.account.register;

public interface RegisterNavigator {

    void handleErrorService(Throwable throwable);

    void register();

    void login();

    void showErrorServer();

    void showErrorOthers();

    void showErrorPhoneDuplicated();

    void showErrorEmailDuplicated();

    void openTermsOfUsage();

    void openPrivacyPolicy();

    void openPhoneNumberVerification();

    void showLoading();

    void closeLoading();

}
