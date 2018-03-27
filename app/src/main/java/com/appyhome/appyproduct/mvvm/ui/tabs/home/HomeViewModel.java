package com.appyhome.appyproduct.mvvm.ui.tabs.home;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.BannerResponse;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    public ObservableField<ArrayList<BannerResponse>> bannersAdapter = new ObservableField<>(null);

    public HomeViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchBanners() {
        getCompositeDisposable().add(getDataManager()
                .fetchBanners().subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.code.equals(ApiCode.OK_200)) {
                        ArrayList<BannerResponse> arrayList = new ArrayList<>(Arrays.asList(response.message));
                        bannersAdapter.set(arrayList);
                    }
                }, Crashlytics::logException));
    }
}
