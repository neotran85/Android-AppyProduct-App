package com.appyhome.appyproduct.mvvm.ui.register;

public interface RegisterNavigator {

    void openMainActivity();

    void handleError(Throwable throwable);

    void register();

}
