package com.appyhome.appyproduct.mvvm.ui.home;


public interface HomeNavigator {

    void goBack();
    void openLoginActivity();
    void handleError(Throwable throwable);
}
