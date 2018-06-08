package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class PaymentViewModel extends BaseViewModel<PaymentNavigator> {
    public static final String PAYMENT_VISA = "visa";
    public static final String PAYMENT_MOLPAY = "molpay";
    public ObservableField<Boolean> isPaymentVisa = new ObservableField<Boolean>(true);
    public ObservableField<Boolean> isPaymentMolpay = new ObservableField<Boolean>(false);

    private VerifyOrderViewModel mVerifyOrderViewModel;
    private FetchUserInfoViewModel mFetchUserInfoViewModel;

    public PaymentViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mVerifyOrderViewModel = new VerifyOrderViewModel(dataManager, schedulerProvider);
        mFetchUserInfoViewModel = new FetchUserInfoViewModel(dataManager, schedulerProvider);

    }

    public void setNavigator(PaymentNavigator navigator) {
        super.setNavigator(navigator);
        mVerifyOrderViewModel.setNavigator(navigator);
        mFetchUserInfoViewModel.setNavigator(navigator);
    }

    public void updateCartsFromServer() {
        mFetchUserInfoViewModel.fetchUserData();
    }

    public boolean isDefaultPaymentMethodsExist() {
        String method = getDefaultPaymentMethod();
        return method != null && method.length() > 0;
    }

    public void updatePaymentMethods() {
        String method = getDefaultPaymentMethod();
        updatePaymentMethods(method);
    }

    private void updatePaymentMethods(String method) {
        isPaymentVisa.set(method.equals(PAYMENT_VISA));
        isPaymentMolpay.set(method.equals(PAYMENT_MOLPAY));
    }

    private String getDefaultPaymentMethod() {
        return getDataManager().getDefaultPaymentMethod(getUserId());
    }

    public void setDefaultPaymentMethod(String method) {
        getDataManager().setDefaultPaymentMethod(getUserId(), method);
        updatePaymentMethods(method);
    }

    public void doVerifyOrder() {
        mVerifyOrderViewModel.doVerifyOrder();
    }
}
