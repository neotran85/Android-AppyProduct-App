package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class FavoriteItemEmptyViewModel extends BaseViewModel<ProductItemNavigator> {

    public ObservableField<Boolean> isFilter = new ObservableField<>(false);

    public FavoriteItemEmptyViewModel(DataManager dataManager,
                                      SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
