package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

public interface ProductListNavigator {
    void handleErrorService(Throwable throwable);
    void showErrorServer();
    void showErrorOthers();
    void showAlert(String message);
}
