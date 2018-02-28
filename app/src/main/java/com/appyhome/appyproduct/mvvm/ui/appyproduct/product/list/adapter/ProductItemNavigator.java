package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryAdapter;

public interface ProductItemNavigator {
    void showContent(CategoryAdapter adapter, View view, int idCategory);
}
