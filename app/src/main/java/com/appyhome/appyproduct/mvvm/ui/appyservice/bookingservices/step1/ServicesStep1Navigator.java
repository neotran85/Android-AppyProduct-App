package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step1;

public interface ServicesStep1Navigator {
    void handleError(Throwable throwable);

    void goToStep2();

    void viewDetailService();

    void viewOurFAQ();

    void viewOurTANDC();

    void viewSuppliesMoreInformation();
}
