package com.appyhome.appyproduct.mvvm.ui.tabs.userpage;


public interface UserPageNavigator {

    void logout();

    void goBack();

    void backToHomeScreen();

    void handleError(Throwable throwable);
}
