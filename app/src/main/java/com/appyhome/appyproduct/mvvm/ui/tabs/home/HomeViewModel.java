package com.appyhome.appyproduct.mvvm.ui.tabs.home;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductListRequest;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {
    public HomeViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<Integer> totalItemsCount = new ObservableField<>(0);

    public void updateTotalProductCart() {
        getCompositeDisposable().add(getDataManager()
                .getTotalProductCarts("1234")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(total -> {
                    if (total >= 0)
                        totalItemsCount.set(total);
                }, throwable -> {
                    Crashlytics.logException(throwable);
                }));
    }
}
