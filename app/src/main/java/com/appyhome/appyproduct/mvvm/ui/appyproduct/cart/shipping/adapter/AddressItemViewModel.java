package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class AddressItemViewModel extends BaseViewModel<AddressItemNavigator> {

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public ObservableField<String> companyName = new ObservableField<>("");
    public ObservableField<String> address = new ObservableField<>("");
    public ObservableField<Boolean> checked = new ObservableField<>(false);

    private int idAddress = 0;

    public AddressItemViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateDefaultToDatabase() {
        getCompositeDisposable().add(getDataManager().setDefaultShippingAddress(getUserId(), idAddress)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    getNavigator().updateDatabaseCompleted();
                }, Crashlytics::logException));
        getCompositeDisposable().add(getDataManager().setUserDefaultShippingAddress(idAddress)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    // SUCCESSFULLY UPDATED
                }, Crashlytics::logException));
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public void removeAddress() {
        getCompositeDisposable().add(getDataManager().removeShippingAddress(getUserId(), idAddress)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    getNavigator().updateDatabaseCompleted();
                }, Crashlytics::logException));
        getCompositeDisposable().add(getDataManager().removeUserShippingAddress(idAddress)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    // SUCCESSFULLY REMOVED FROM SERVER DB
                }, Crashlytics::logException));
    }
}
