package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class FilterViewModel extends BaseViewModel<FilterNavigator> {

    public FilterViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateFilter(String shippingFrom, String discount, String priceMin, String priceMax, float rating) {
        getCompositeDisposable().add(getDataManager().saveProductFilter(getUserId(), shippingFrom,
                discount, rating, priceMin, priceMax)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    getNavigator().updateUIFilter(filter);
                }, Crashlytics::logException));
    }

    public void getCurrentFilter() {
        getCompositeDisposable().add(getDataManager().getCurrentFilter(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    getNavigator().updateUIFilter(filter);
                }, Crashlytics::logException));
    }

    public void resetFilter() {
        getCompositeDisposable().add(getDataManager().saveProductFilter(getUserId(), "",
                "", -1, "", "")
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    getNavigator().updateUIFilter(filter);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

}
