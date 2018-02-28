package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.CategoryAdapter;

public interface ProductItemNavigator {
    void showContent(CategoryAdapter adapter, View view, int idCategory);
}
