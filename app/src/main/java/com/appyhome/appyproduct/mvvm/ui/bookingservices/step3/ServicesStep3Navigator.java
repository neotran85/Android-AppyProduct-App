package com.appyhome.appyproduct.mvvm.ui.bookingservices.step3;

public interface ServicesStep3Navigator {
    void handleError(Throwable throwable);
    void goToStep4();
    void doWhenAppointmentCreated();
    void openLoginActivity();
}
