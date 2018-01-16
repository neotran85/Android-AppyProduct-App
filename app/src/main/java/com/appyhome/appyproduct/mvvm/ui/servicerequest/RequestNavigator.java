package com.appyhome.appyproduct.mvvm.ui.servicerequest;


public interface RequestNavigator {

    void goBack();

    void handleError(Throwable throwable);
}
