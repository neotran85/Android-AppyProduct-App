package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {
    public ObservableField<Boolean> isHistoryVisible = new ObservableField<>(true);
    public ObservableField<Boolean> isClearable = new ObservableField<>(false);
    private int mCounter = 0;
    private int mCount = 0;
    private HashMap<ProductTopic, String> mSearchTopic = new HashMap<>();

    public SearchViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void clearSearchHistory() {
        getCompositeDisposable().add(getDataManager().clearSearchHistory(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    if (success) {
                        isHistoryVisible.set(false);
                        getNavigator().updateUISearchHistory(null);
                    }
                }, Crashlytics::logException));
    }

    public void getSearchHisTory() {
        getCompositeDisposable().add(getDataManager().getSearchHistory(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(searchItems -> {
                    isHistoryVisible.set(searchItems != null && searchItems.size() > 0);
                    getNavigator().updateUISearchHistory(searchItems);
                }, Crashlytics::logException));
    }

    public void addSearchItems(SearchItem[] items) {
        ArrayList<SearchItem> arrayList = new ArrayList<>(Arrays.asList(items));
        getCompositeDisposable().add(getDataManager().addSearchItems(arrayList)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    getNavigator().doAfterSearchItemsAdded();
                }, Crashlytics::logException));
    }

    public void getSearchSuggestions() {
        getCompositeDisposable().add(getDataManager().getSearchSuggestions()
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(suggestions -> {
                    getNavigator().showSuggestions(suggestions);
                }, Crashlytics::logException));
    }

    public void getAllProductTopics() {
        getCompositeDisposable().add(getDataManager().getAllProductTopics()
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(topics -> {
                    // DONE GET
                    mCount = topics.size();
                    for (ProductTopic topic : topics) {
                        getProductCategoryIdsByTopic(topic);
                    }
                }, Crashlytics::logException));
    }

    private void getProductCategoryIdsByTopic(ProductTopic topic) {
        getCompositeDisposable().add(getDataManager().getProductCategoryIdsByTopic(topic.id)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(subs -> {
                    ArrayList<Integer> ids = new ArrayList<>();
                    for (ProductSub item : subs) {
                        ids.add(item.id);
                    }
                    mSearchTopic.put(topic, TextUtils.join(",", ids));
                    mCounter++;
                    if (mCounter == mCount) {
                        getNavigator().updateUISearchCategory(mSearchTopic);
                    }
                }, Crashlytics::logException));
    }
}
