package com.appyhome.appyproduct.mvvm.ui.bookingservices.step5;

import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateResponse;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.AppLogger;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

public class ServicesStep5ViewModel extends BaseViewModel<ServicesStep5Navigator> {
    private final ObservableField<String> orderId = new ObservableField<>("");
    private final ObservableField<Boolean> isOrderSuccess = new ObservableField<>(false);

    public ServicesStep5ViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        orderId.set(getDataManager().getServiceOrderUserInput().getAppointmentId());
    }

    public String getOrderId() {
        return "Order Id: #" + orderId.get();
    }

    public int isCongratulationsVisible() {
        return isOrderSuccess.get() == true ? View.VISIBLE : View.GONE;
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
                        if (response.getStatusCode().equals(ApiCode.OK_200)) {
                            getNavigator().showCongratulationForm();
                            isOrderSuccess.set(true);
                            getDataManager().getServiceOrderUserInput().clear();
                        } else {
                            getNavigator().handleErrorService(null);
                            isOrderSuccess.set(false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleErrorService(throwable);
                        isOrderSuccess.set(false);
                    }
                }));
    }
}
