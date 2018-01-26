package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

public interface EditDetailNavigator {

    void handleErrorService(Throwable a);
    void goBackAfterSubmitting(String data);
}
