package com.appyhome.appyproduct.mvvm.ui.bookingservices.step3;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ServicesStep3ViewModel extends BaseViewModel<ServicesStep3Navigator> {

    public ObservableField<String> number = new ObservableField<>("");
    public ObservableField<String> street = new ObservableField<>("");
    public ObservableField<String> area1 = new ObservableField<>("");
    public ObservableField<String> area2 = new ObservableField<>("");
    public ObservableField<String> city = new ObservableField<>("");
    public ObservableField<String> code = new ObservableField<>("");

    public ServicesStep3ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ServiceAddress getServiceAddress() {
        return getDataManager().loadServiceAddress();
    }

    public void saveServiceAddress(ServiceAddress serviceAddress) {
        getDataManager().saveServiceAddress(serviceAddress);
    }

    public void updateAddress() {
        ServiceAddress address = getServiceAddress();
        number.set(address.number);
        street.set(address.street);
        area1.set(address.area1);
        area2.set(address.area2);
        city.set(address.city);
        code.set(address.code);
    }
}
