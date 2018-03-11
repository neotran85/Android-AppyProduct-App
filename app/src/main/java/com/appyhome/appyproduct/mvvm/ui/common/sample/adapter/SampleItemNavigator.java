package com.appyhome.appyproduct.mvvm.ui.common.sample.adapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryAdapter;

public interface SampleItemNavigator {
    void showContent(SampleItemAdapter adapter, View view, int idCategory);
}
