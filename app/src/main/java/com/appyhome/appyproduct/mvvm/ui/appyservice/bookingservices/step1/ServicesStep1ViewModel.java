package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step1;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyService;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

public class ServicesStep1ViewModel extends BaseViewModel<ServicesStep1Navigator> {
    private int mTypeServices = 0;
    public final ObservableField<Boolean> isAirConCleaningVisible = new ObservableField<>(false);

    public ServicesStep1ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public int getTypeServices() {
        return mTypeServices;
    }

    public void setTypeServices(int type) {
        mTypeServices = type;
        isAirConCleaningVisible.set(mTypeServices == ServiceOrderUserInput.SERVICE_AIR_CON_CLEANING);
    }

    public ArrayList<AppyService> getFilteredServices(String tags) {
        ArrayList<AppyService> filteredServices = new ArrayList<>();
        ArrayList<AppyService> services = getDataManager().getServiceOrderUserInput().getServices();
        if (services != null && services.size() > 0) {
            for (AppyService service : services) {
                if (tags.contains(service.tags)) {
                    filteredServices.add(service);
                }
            }
        }
        return filteredServices;
    }
}
