package com.appyhome.appyproduct.mvvm.ui.bookingservices.step4;

public interface ServicesStep4Navigator {

    void goToStep5();

    void doWhenAppointmentCreated();

    void openBankPaymentActivity();

    void handleErrorService(Throwable throwable);
}
