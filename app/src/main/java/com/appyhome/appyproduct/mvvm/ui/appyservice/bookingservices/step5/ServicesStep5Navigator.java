package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step5;

public interface ServicesStep5Navigator {

    void openRequestsScreen();

    void backToHomeScreen();

    void showCongratulationForm();

    void handleErrorService(Throwable throwable);
}
