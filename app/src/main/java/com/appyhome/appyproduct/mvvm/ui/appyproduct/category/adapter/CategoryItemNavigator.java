package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import android.view.View;

public interface CategoryItemNavigator {
    void showContent(CategoryAdapter adapter, View view, int idCategory);
    void showContent(SubCategoryAdapter adapter, View view, int idCategory);
}
