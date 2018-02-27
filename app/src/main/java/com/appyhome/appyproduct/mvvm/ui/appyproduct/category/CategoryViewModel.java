package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class CategoryViewModel extends BaseViewModel<CategoryNavigator> {
    public CategoryViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getProductCategoryByTopic(int idTopic) {
        getCompositeDisposable().add(getDataManager().getProductCategoryByTopic(idTopic)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> {
                    // DONE
                    getNavigator().showCategories(categories);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
