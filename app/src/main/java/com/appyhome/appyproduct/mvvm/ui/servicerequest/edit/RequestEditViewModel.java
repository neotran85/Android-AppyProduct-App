package com.appyhome.appyproduct.mvvm.ui.servicerequest.edit;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class RequestEditViewModel extends RequestItemViewModel {

    public RequestEditViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateDetailChange(String comments, String amount) {
        setIsLoading(true);
    }
}
