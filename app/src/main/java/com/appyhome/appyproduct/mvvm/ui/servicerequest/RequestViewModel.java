package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentGetResponse;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class RequestViewModel extends BaseViewModel<RequestNavigator> {

    public RequestViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllAppointments() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAppointmentAll()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<AppointmentGetResponse>() {
                    @Override
                    public void accept(AppointmentGetResponse response) throws Exception {
                        setIsLoading(false);
                        handleServiceResult(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    private void handleServiceResult(AppointmentGetResponse response) {
        if(response!= null && response.getStatusCode() == "200") {
            AppLogger.d(response.getResult());
        }
    }

}
