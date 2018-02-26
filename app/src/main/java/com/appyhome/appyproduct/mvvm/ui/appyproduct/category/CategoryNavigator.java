package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

public interface CategoryNavigator {
    void handleErrorService(Throwable throwable);
    void showErrorServer();
    void showErrorOthers();
    void showAlert(String message);
}
