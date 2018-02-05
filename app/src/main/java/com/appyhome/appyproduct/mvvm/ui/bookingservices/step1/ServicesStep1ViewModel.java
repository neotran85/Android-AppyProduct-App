package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class ServicesStep1ViewModel extends BaseViewModel<ServicesStep1Navigator> {
    private final ObservableField<Integer> typeServices = new ObservableField<>(0);
    private final ObservableField<Integer> isAirConCleaningVisible = new ObservableField<>(View.GONE);
    private final ObservableField<Integer> isHomeCleaningVisible = new ObservableField<>(View.GONE);

    public ServicesStep1ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public int isAirConCleaningVisible() {
        return typeServices.get() == ServiceOrderUserInput.SERVICE_AIR_CON_CLEANING ? View.VISIBLE : View.GONE;
    }

    public int isHomeCleaningVisible() {
        return typeServices.get() == ServiceOrderUserInput.SERVICE_HOME_CLEANING ? View.VISIBLE : View.GONE;
    }

    public ObservableField<Integer> getTypeServices() {
        return typeServices;
    }

    public void setTypeServices(int type) {
        typeServices.set(type);
        isAirConCleaningVisible.set(isAirConCleaningVisible());
        isHomeCleaningVisible.set(isHomeCleaningVisible());
    }
}
