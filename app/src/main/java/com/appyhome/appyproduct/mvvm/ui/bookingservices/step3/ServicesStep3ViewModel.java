package com.appyhome.appyproduct.mvvm.ui.bookingservices.step3;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.appointment.AppointmentCreateRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.appointment.AppointmentCreateResponse;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceAddress;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class ServicesStep3ViewModel extends BaseViewModel<ServicesStep3Navigator> {

    public ServicesStep3ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void createAppointment(AppointmentCreateRequest requestData) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .createAppointment(requestData)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<AppointmentCreateResponse>() {
                    @Override
                    public void accept(AppointmentCreateResponse response) throws Exception {
                        setIsLoading(false);
                        getNavigator().doWhenAppointmentCreated();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
                        getNavigator().doWhenAppointmentCreated();
                    }
                }));
    }

    private void handleResponse(AppointmentCreateResponse response) {
        if (response != null) {
            if (response.getStatusCode() != null && response.getStatusCode() == "401") {
                getNavigator().openLoginActivity();
            }
        }
    }

    public ServiceAddress getServiceAddress() {
        return getDataManager().getServiceAddress();
    }

    public void setServiceAddress(ServiceAddress serviceAddress) {
        getDataManager().setServiceAddress(serviceAddress);
    }
}
