package com.appyhome.appyproduct.mvvm.ui.servicerequest;


import java.util.ArrayList;

public interface RequestNavigator {

    void goBack();

    void showResultList(ArrayList<RequestItemViewModel> array);

    void handleError(Throwable throwable);
}
