package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class CategoryViewModel extends BaseViewModel<CategoryNavigator> {
    public ObservableField<String> title = new ObservableField<>("");

    public ObservableField<Float> widthLeftMenu = new ObservableField<>(0f);
    public ObservableField<Float> widthItem = new ObservableField<>(0f);


    public CategoryViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private void getProductCategoriesByTopic(int idTopic) {
        getCompositeDisposable().add(getDataManager().getProductCategoriesByTopic(idTopic)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> {
                    getNavigator().showCategories(categories);
                }, Crashlytics::logException));
    }

    public void getProductTopicById(int idTopic) {
        getCompositeDisposable().add(getDataManager().getProductTopicById(idTopic)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(topic -> {
                    getProductCategoriesByTopic(idTopic);
                    title.set(topic.name);
                }, Crashlytics::logException));
    }

    public void getProductSubCategoryByCategory(int idCategory) {
        getCompositeDisposable().add(getDataManager().getSubProductCategoryByCategory(idCategory)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(subs -> {
                    // DONE
                    getNavigator().showSubCategories(subs);
                }, Crashlytics::logException));
    }
}
