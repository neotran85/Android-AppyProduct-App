package com.appyhome.appyproduct.mvvm.ui.bookingservices.step3;

public interface ServicesStep3Navigator {
    void handleErrorService(Throwable throwable);

    void goToStep4();

    void doWhenAppointmentCreated();

    void openLoginActivity();
}
