package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step3;

public interface ServicesStep3Navigator {
    void goToStep4();

    void openLoginActivity(String message, int requestCode);

    void showAlert(String message);
}
