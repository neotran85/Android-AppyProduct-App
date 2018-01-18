package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

public interface ServicesStep1Navigator {
    void handleError(Throwable throwable);
    void goToStep2();
    void viewDetailService();
}
