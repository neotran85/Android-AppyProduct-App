package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.view.View;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;

import java.util.ArrayList;

import io.realm.RealmResults;

public interface SearchNavigator {
    void showAlert(String message);

    void goBack();

    void updateUISearchHistory(RealmResults<SearchItem> items);

    void updateUISearchCategory(RealmResults<ProductTopic> items);

    void doAfterSearchItemsAdded();

    void clickSearchHistoryItem(View view);

    void clearKeywords();

    void clearHistory();

    void search();

    void showSuggestions(RealmResults<SearchItem> items);

    void clickSearchCategoryItem(View view);

    void addProductCategoryIdsByTopic(int idTopic, ArrayList<Integer> ids);
}
