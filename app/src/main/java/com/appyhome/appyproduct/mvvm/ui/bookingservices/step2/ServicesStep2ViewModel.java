package com.appyhome.appyproduct.mvvm.ui.bookingservices.step2;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

public class ServicesStep2ViewModel extends BaseViewModel<ServicesStep2Navigator> {

    public ServicesStep2ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateServiceOrderInfo(String timeSlot1, String timeSlot2, String timeSlot3, ArrayList<String> extraServices) {
        getDataManager().getServiceOrderUserInput().setTimeSlot1(timeSlot1);
        getDataManager().getServiceOrderUserInput().setTimeSlot2(timeSlot2);
        getDataManager().getServiceOrderUserInput().setTimeSlot3(timeSlot3);
        getDataManager().getServiceOrderUserInput().setExtraServices(extraServices);
    }
}
