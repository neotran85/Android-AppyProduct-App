package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.payment.PaymentViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import io.realm.RealmResults;

public class ConfirmationViewModel extends BaseViewModel<ConfirmationNavigator> {
    public ObservableField<String> totalCost = new ObservableField<>("");
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public ObservableField<String> address = new ObservableField<>("");
    public ObservableField<Boolean> isVisa = new ObservableField<>(false);
    public ObservableField<Boolean> isMolpay = new ObservableField<>(false);

    private float mTotalCost = 0;
    private RealmResults<ProductCart> mCarts;
    private String mPaymentMethod = "";
    private Address mShippingAddress;
    private String customerName = "";

    public ConfirmationViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getDefaultShippingAddress() {
        getCompositeDisposable().add(getDataManager().getDefaultShippingAddress(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(addressResult -> {
                    // GET SUCCEEDED
                    mShippingAddress = addressResult;
                    name.set(addressResult.recipient_name);
                    phoneNumber.set(addressResult.recipient_phone_number);
                    address.set(addressResult.getAddressText());
                    customerName = addressResult.recipient_name;
                }, Crashlytics::logException));
    }

    public void fetchPaymentMethods() {
        mPaymentMethod = getDataManager().getDefaultPaymentMethod(getUserId());
        isVisa.set(mPaymentMethod.equals(PaymentViewModel.PAYMENT_VISA));
        isMolpay.set(mPaymentMethod.equals(PaymentViewModel.PAYMENT_MOLPAY));
    }

    public void addOrder(long orderId) {
        getCompositeDisposable().add(getDataManager().addOrder(mCarts, mPaymentMethod,
                mShippingAddress, getUserId(), customerName, mTotalCost, 0, orderId)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(order -> {
                    // GET SUCCEEDED
                    if (order != null && order.isValid()) {
                        //getNavigator().showAlert("Order added");
                        getNavigator().addOrderOk(order);
                    } else {
                        getNavigator().handleErrors(null);
                    }
                }, throwable -> {
                    getNavigator().handleErrors(throwable);
                    Crashlytics.logException(throwable);
                }));
    }

    public void getAllCheckedProductCarts() {
        getCompositeDisposable().add(getDataManager().getAllCheckedProductCarts(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(items -> {
                    // GET SUCCEEDED
                    mCarts = items;
                    getNavigator().showCheckedItems(items);
                    if (items != null && items.size() > 0) {
                        mTotalCost = 0;
                        for (ProductCart item : items) {
                            mTotalCost = mTotalCost + (item.price * item.amount);
                        }
                        mTotalCost = DataUtils.roundNumber(mTotalCost, 2);
                        totalCost.set(mTotalCost + "");
                    }
                }, Crashlytics::logException));
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
