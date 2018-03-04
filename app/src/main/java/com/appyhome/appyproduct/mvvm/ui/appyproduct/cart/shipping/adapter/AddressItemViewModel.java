package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class AddressItemViewModel extends BaseViewModel<AddressItemNavigator> {

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public ObservableField<String> address = new ObservableField<>("");
    public ObservableField<Boolean> checked = new ObservableField<>(false);

    private long idAddress = 0;

    public AddressItemViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateDefaultToDatabase() {
        getCompositeDisposable().add(getDataManager().setDefaultShippingAddress("1234", idAddress)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // UPDATE DEFAULT ADDRESS SUCCEEDED
                    getNavigator().updateDatabaseCompleted(this);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    public void setIdAddress(long idAddress) {
        this.idAddress = idAddress;
    }
}
