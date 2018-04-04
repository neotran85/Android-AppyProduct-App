package com.appyhome.appyproduct.mvvm.ui.common.component.cart;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleItemNavigator;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class SearchToolbarViewModel extends BaseViewModel<SampleItemNavigator> {
    public ObservableField<Integer> totalItemsCount = new ObservableField<>(0);
    public ObservableField<Boolean> isFullMode = new ObservableField<>(true);
    public ObservableField<Boolean> isBackButtonShowed = new ObservableField<>(false);
    public ObservableField<String> keywords = new ObservableField<>("");
    public ObservableField<Boolean> hasKeywords = new ObservableField<>(false);

    public SearchToolbarViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateTotalCountProductCart() {
        getCompositeDisposable().add(getDataManager()
                .getTotalCountProductCarts(getUserId())
                .take(1)
                .subscribeOn(getSchedulerProvider().newThread())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(total -> {
                    if (total >= 0)
                        totalItemsCount.set(total);
                }, Crashlytics::logException));
    }
}
