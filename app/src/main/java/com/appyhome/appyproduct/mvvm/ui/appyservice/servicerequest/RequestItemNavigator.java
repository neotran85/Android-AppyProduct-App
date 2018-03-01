package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest;

public interface RequestItemNavigator {
    void handleErrorService(Throwable a);

    void doAfterDataUpdated();
}
