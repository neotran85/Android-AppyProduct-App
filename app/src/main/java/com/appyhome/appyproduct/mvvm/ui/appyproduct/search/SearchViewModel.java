package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {
    public ObservableField<Boolean> isHistoryVisible = new ObservableField<>(true);
    public ObservableField<Boolean> isSuggestionsVisible = new ObservableField<>(false);
    public ObservableField<Boolean> isClearable = new ObservableField<>(false);
    public SearchViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void clearSearchHistory() {
        getCompositeDisposable().add(getDataManager().clearSearchHistory(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    getNavigator().updateUISearchHistory(null);
                }, Crashlytics::logException));
    }

    public void getSearchHisTory() {
        getCompositeDisposable().add(getDataManager().getSearchHistory(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(searchItems -> {
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
}
