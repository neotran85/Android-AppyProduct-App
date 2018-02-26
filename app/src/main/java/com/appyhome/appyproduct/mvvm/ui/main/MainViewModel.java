package com.appyhome.appyproduct.mvvm.ui.main;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class MainViewModel extends BaseViewModel<MainNavigator> {
    public MainViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllProductTopics() {
        getCompositeDisposable().add(getDataManager().getProductCategoryByTopic(70)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> {
                    // DONE
                    getNavigator().showAlert(categories.size() + "");
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
