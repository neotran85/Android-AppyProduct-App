package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort.SortOption;
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
                .observeOn(getSchedulerProvider().ui())
                .subscribe(value -> {

                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    public void getCurrentFilter() {
        getCompositeDisposable().add(getDataManager().getCurrentFilter(getUserId())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    getNavigator().updateUIFilter(filter);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

}
