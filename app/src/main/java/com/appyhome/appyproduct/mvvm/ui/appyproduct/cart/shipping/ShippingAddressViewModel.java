package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import io.realm.RealmResults;

public class ShippingAddressViewModel extends BaseViewModel<ShippingAddressNavigator> implements VerifyOrderNavigator {
    public ObservableField<Boolean> isNoAddress = new ObservableField<>(true);
    public ObservableField<Boolean> isEditMode = new ObservableField<Boolean>(false);

    private VerifyOrderViewModel mVerifyOrderViewModel;

    public ShippingAddressViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mVerifyOrderViewModel = new VerifyOrderViewModel(dataManager, schedulerProvider);
        mVerifyOrderViewModel.setNavigator(this);
    }

    public void getAllShippingAddress() {
        getCompositeDisposable().add(getDataManager().getAllShippingAddress(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe((RealmResults<AppyAddress> addresses) -> {
                    isNoAddress.set(addresses == null || !addresses.isValid() || addresses.size() <= 0);
                    getNavigator().showAddressList(addresses);
                }, Crashlytics::logException));
    }

    public void updateShippingFees() {
        mVerifyOrderViewModel.doVerifyShippingFee();
    }

    @Override
    public void verifyOrder_PASSED() {
        getNavigator().updateShippingFees_DONE();
    }

    @Override
    public void verifyOrder_FAILED(String message) {
        getNavigator().updateShippingFees_DONE();
    }
}
