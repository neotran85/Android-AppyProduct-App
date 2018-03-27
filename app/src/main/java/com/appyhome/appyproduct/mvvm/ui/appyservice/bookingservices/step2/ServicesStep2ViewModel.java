package com.appyhome.appyproduct.mvvm.ui.appyservice.bookingservices.step2;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

public class ServicesStep2ViewModel extends BaseViewModel<ServicesStep2Navigator> {

    public ObservableField<Boolean> isExtraServiceVisible = new ObservableField<>(false);

    public ServicesStep2ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        int type = getDataManager().getServiceOrderUserInput().getType();
        isExtraServiceVisible.set(type == ServiceOrderUserInput.SERVICE_HOME_CLEANING);
    }

    public void updateServiceOrderInfo(String timeSlot1, String timeSlot2, String timeSlot3, ArrayList<String> extraServices) {
        getDataManager().getServiceOrderUserInput().setTimeSlot1(timeSlot1);
        getDataManager().getServiceOrderUserInput().setTimeSlot2(timeSlot2);
        getDataManager().getServiceOrderUserInput().setTimeSlot3(timeSlot3);
        getDataManager().getServiceOrderUserInput().setExtraServices(extraServices);
    }
}
