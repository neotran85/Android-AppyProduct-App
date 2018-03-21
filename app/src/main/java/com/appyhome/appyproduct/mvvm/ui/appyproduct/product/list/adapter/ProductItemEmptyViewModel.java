package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ProductItemEmptyViewModel extends BaseViewModel<ProductItemNavigator> {

    public ObservableField<Boolean> isFilter = new ObservableField<>(false);

    public ProductItemEmptyViewModel(DataManager dataManager,
                                     SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private void updateCountFilter(ProductFilter filter) {
        int count = 0;
        if (filter.shipping_from.length() > 0) {
            count++;
        }
        if (filter.discount.length() > 0) {
            count++;
        }
        if (filter.rating > 0) {
            count++;
        }
        if (filter.price_max > 0) {
            count++;
        }
        if (filter.price_min > 0) {
            count++;
        }
        isFilter.set(count > 0);
    }

    public void getCurrentFilter() {
        getCompositeDisposable().add(getDataManager().getCurrentFilter(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(filter -> {
                    updateCountFilter(filter);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
