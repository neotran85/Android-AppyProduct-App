package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.view.View;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmResults;

public interface SearchNavigator {
    void showAlert(String message);

    void goBack();

    void updateUISearchHistory(RealmResults<SearchItem> items);

    void updateUISearchCategory(HashMap<ProductTopic, String> items);

    void doAfterSearchItemsAdded();

    void clickSearchHistoryItem(View view);

    void clearKeywords();

    void clearHistory();

    void search();

    void showSuggestions(RealmResults<SearchItem> items);

    void clickSearchCategoryItem(View view);
}
