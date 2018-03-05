package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ConfirmationViewModel extends BaseViewModel<ConfirmationNavigator> {
    public ObservableField<String> totalCost = new ObservableField<>("");
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public ObservableField<String> address = new ObservableField<>("");
    public ObservableField<Boolean> isVisa = new ObservableField<>(false);
    public ObservableField<Boolean> isMolpay = new ObservableField<>(false);

    private float mTotalCost = 0;

    public ConfirmationViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getDefaultShippingAddress() {
        getCompositeDisposable().add(getDataManager().getDefaultShippingAddress("1234")
                .observeOn(getSchedulerProvider().ui())
                .subscribe(addressResult -> {
                    // GET SUCCEEDED
                    name.set(addressResult.customer_name);
                    phoneNumber.set(addressResult.phone_number);
                    address.set(addressResult.address);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    public void fetchPaymentMethods() {
        String paymentMethod = getDataManager().getDefaultPaymentMethod("1234");
        isVisa.set(paymentMethod.equals(PaymentViewModel.PAYMENT_VISA));
        isMolpay.set(paymentMethod.equals(PaymentViewModel.PAYMENT_MOLPAY));
    }

    public void getAllCheckedProductCarts() {
        getCompositeDisposable().add(getDataManager().getAllCheckedProductCarts("1234")
                .observeOn(getSchedulerProvider().ui())
                .subscribe(items -> {
                    // GET SUCCEEDED
                    getNavigator().showCheckedItems(items);
                    if (items != null && items.size() > 0) {
                        mTotalCost = 0;
                        for (ProductCart item : items) {
                            mTotalCost = mTotalCost + (item.price * item.amount);
                        }
                        totalCost.set(mTotalCost + "");
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
    public String getOrderId() {
        return System.currentTimeMillis() + "";
    }
    public String getNameOfUser() {
        String firstName = getDataManager().getUserFirstName();
        String lastName = getDataManager().getUserLastName();
        if (firstName != null && firstName.length() > 0)
            return firstName + " " + lastName;
        return "";
    }

    public String getEmailOfUser() {
        return getDataManager().getCurrentUserEmail();
    }

    public String getPhoneNumberOfUser() {
        return getDataManager().getCurrentPhoneNumber();
    }
}
