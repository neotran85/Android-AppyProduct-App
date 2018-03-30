package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.view.View;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;

import io.realm.RealmResults;

public interface SearchNavigator {
    void showAlert(String message);
    void goBack();
    void updateUISearchHistory(RealmResults<SearchItem> items);
    void doAfterSearchItemsAdded();
    void clickSearchHistoryItem(View view);
    void clearKeywords();
    void clearHistory();
    void search();

}
