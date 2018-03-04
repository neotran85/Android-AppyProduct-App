package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.util.Observable;

import io.reactivex.disposables.Disposable;

public class ShippingAddressViewModel extends BaseViewModel<ShippingAddressNavigator> {
    public ObservableField<Boolean> isNoAddress = new ObservableField<>(true);
    Disposable getAllShippingAddress = null;
    public ShippingAddressViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void disposeGetAllShippingAddress() {
        if(getAllShippingAddress != null)
            getAllShippingAddress.dispose();
    }

    public void getAllShippingAddress() {
        getAllShippingAddress = getDataManager().getAllShippingAddress("1234")
                .observeOn(getSchedulerProvider().ui())
                .subscribe(addresses -> {
                    boolean isValid = addresses != null && addresses.isValid();
                    if (isValid) {
                        getNavigator().showAddressList(addresses);
                    }
                    isNoAddress.set(isValid && addresses.size() > 0 ? false : true);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                });
        getCompositeDisposable().add(getAllShippingAddress);
    }

}
