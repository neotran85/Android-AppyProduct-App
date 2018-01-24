package com.appyhome.appyproduct.mvvm.ui.bookingservices.step5;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateResponse;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class ServicesStep5ViewModel extends BaseViewModel<ServicesStep5Navigator> {

    public ServicesStep5ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void createAppointment(AppointmentCreateRequest requestData) {
        setIsLoading(true);
        AppLogger.d(requestData.toString());
        getCompositeDisposable().add(getDataManager()
                .createAppointment(requestData)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<AppointmentCreateResponse>() {
                    @Override
                    public void accept(AppointmentCreateResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.getStatusCode().equals("200")) {
                            getNavigator().showCongratulationForm();
                            AppLogger.d(response.getMessage());
                        } else getNavigator().handleErrorService(null);
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
