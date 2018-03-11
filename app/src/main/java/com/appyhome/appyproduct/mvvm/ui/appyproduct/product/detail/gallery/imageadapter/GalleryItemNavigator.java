package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.imageadapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryAdapter;

public interface GalleryItemNavigator {
    void showContent(CategoryAdapter adapter, View view, int idCategory);
}
