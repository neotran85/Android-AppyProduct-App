package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import android.databinding.ObservableArrayList;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.RequestResponse;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

public class RequestViewModel extends BaseViewModel<RequestNavigator> {
    public String temp;
    private final ObservableArrayList<RequestResponse.Request> requestObservableArrayList = new ObservableArrayList<>();
    public RequestViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void addRequestItemsToList(List<RequestResponse.Request> list) {
        requestObservableArrayList.clear();
        requestObservableArrayList.addAll(list);
    }

    public ObservableArrayList<RequestResponse.Request> getRequestObservableArrayList() {
        return requestObservableArrayList;
    }
}
