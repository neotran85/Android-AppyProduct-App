package com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryAdapter;

public interface SearchItemNavigator {
    void showContent(SearchAdapter adapter, View view, int idCategory);
}
