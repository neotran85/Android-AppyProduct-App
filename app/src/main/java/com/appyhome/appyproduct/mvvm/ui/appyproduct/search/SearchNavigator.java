package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.text.Editable;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;

import java.util.HashMap;

import io.realm.RealmResults;

public interface SearchNavigator {
    void showAlert(String message);

    void goBack();

    void updateUISearchHistory(RealmResults<SearchItem> items);

    void updateUISearchCategory(HashMap<ProductTopic, String> items);

    void doAfterSearchItemsAdded();

    void onSearchHistoryClick(SearchItem data);

    void clearKeywords();

    void clearHistory();

    void search();

    void showSuggestions(RealmResults<SearchItem> items);

    void onProductTopicClick(View view, ProductTopic topic);

    void onAfterTextChanged(Editable s);
}
