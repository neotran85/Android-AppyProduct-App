package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.confirm;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderCompletedRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class RequestConfirmedViewModel extends RequestItemViewModel {

    public RequestConfirmedViewModel(DataManager dataManager,
                                     SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void markOrderCompleted(String comments, String rating) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .markServiceOrderCompleted(new OrderCompletedRequest(getIdNumber(), comments, rating))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getStatusCode().equals(ApiCode.OK_200)) {
                        // MARK COMPLETED SUCCESS
                        setIsLoading(true);
                        getNavigator().doAfterDataUpdated();
                    }
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleErrorService(throwable);
                }));
    }
}
