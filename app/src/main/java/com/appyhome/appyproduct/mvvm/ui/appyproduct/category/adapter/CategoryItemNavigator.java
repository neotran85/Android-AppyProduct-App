package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryAdapter;

public interface CategoryItemNavigator {
    void showContent(CategoryAdapter adapter, View view, int idCategory);
}
