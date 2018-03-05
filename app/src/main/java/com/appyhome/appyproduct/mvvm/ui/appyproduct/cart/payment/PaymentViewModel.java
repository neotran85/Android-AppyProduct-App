package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class PaymentViewModel extends BaseViewModel<PaymentNavigator> {
    public static final String PAYMENT_VISA = "visa";
    public static final String PAYMENT_MOLPAY = "molpay";

    public ObservableField<Boolean> isEditMode = new ObservableField<Boolean>(false);
    public ObservableField<Boolean> isPaymentVisa = new ObservableField<Boolean>(true);
    public ObservableField<Boolean> isPaymentMolpay = new ObservableField<Boolean>(false);

    public PaymentViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void fetchPaymentMethods() {
        String method = getDefaultPaymentMethod();
        fetchPaymentMethods(method);
    }
    public void fetchPaymentMethods(String method) {
        isPaymentVisa.set(method.equals(PAYMENT_VISA));
        isPaymentMolpay.set(method.equals(PAYMENT_MOLPAY));
    }
    private String getDefaultPaymentMethod() {
        return getDataManager().getDefaultPaymentMethod("1234");
    }

    public void setDefaultPaymentMethod(String method) {
        getDataManager().setDefaultPaymentMethod("1234", method);
        fetchPaymentMethods(method);
    }
}
