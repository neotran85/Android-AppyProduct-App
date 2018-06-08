package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.detail;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ProductTrackingViewModel extends BaseViewModel<ProductTrackingNavigator> {
    public ObservableField<String> fieldOrderId = new ObservableField<>("");
    public ObservableField<String> fieldPaymentMethod = new ObservableField<>("");

    public ProductTrackingViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getOrderById(long idOrder) {
        getCompositeDisposable().add(getDataManager().getOrderById(getUserId(), idOrder)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(order -> {
                    // GET SUCCEEDED
                    fieldOrderId.set(order.id + "");
                    getNavigator().showOrder(order);
                }, Crashlytics::logException));
    }


}
