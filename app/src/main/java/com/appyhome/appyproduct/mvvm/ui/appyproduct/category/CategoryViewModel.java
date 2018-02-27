package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class CategoryViewModel extends BaseViewModel<CategoryNavigator> {
    public ObservableField<String> title = new ObservableField<>("");
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

    public void getProductTopicById(int idTopic) {
        getCompositeDisposable().add(getDataManager().getProductTopicById(idTopic)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(topic -> {
                    // DONE
                    title.set(topic.name);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }

    public void getProductSubCategoryByCategory(int idCategory) {
        getCompositeDisposable().add(getDataManager().getSubProductCategoryByCategory(idCategory)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(subs -> {
                    // DONE
                    getNavigator().showSubCategories(subs);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
