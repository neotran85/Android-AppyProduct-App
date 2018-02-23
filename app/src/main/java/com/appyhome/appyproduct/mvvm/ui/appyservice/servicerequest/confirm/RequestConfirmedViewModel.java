package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.confirm;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderCompletedRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.OrderCompletedResponse;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest.RequestItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class RequestConfirmedViewModel extends RequestItemViewModel {

    public RequestConfirmedViewModel(DataManager dataManager,
                                     SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void markOrderCompleted(String comments, String rating) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .markOrderCompleted(new OrderCompletedRequest(getIdNumber(), comments, rating))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<OrderCompletedResponse>() {
                    @Override
                    public void accept(OrderCompletedResponse response) throws Exception {
                        if (response != null && response.getStatusCode().equals(ApiCode.OK_200)) {
                            // MARK COMPLETED SUCCESS
                            setIsLoading(true);
                            getNavigator().doAfterDataUpdated();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
                    }
                }));
    }
}
