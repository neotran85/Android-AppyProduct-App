package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.visa;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class VisaPaymentViewModel extends BaseViewModel<VisaPaymentNavigator> {
    public VisaPaymentViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
