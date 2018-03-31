package com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

public class SearchItemViewModel extends BaseViewModel<SearchItemNavigator> {
    public ObservableField<String> keyword = new ObservableField<>("");

    public SearchItemViewModel(String keywordValue, SearchItemNavigator navigator) {
        this.setNavigator(navigator);
        keyword.set(keywordValue);
    }
}
