package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;

import java.util.ArrayList;

import io.realm.RealmResults;

public interface ProductListNavigator {
    void handleErrorService(Throwable throwable);

    void showAlert(String message);

    void showProducts(RealmResults<Product> result);

    void showProducts(Product[] list);

    void updateFavorites(ArrayList<Integer> listId);

    void showEmptyProducts();

    void toggleSortOptions();

    void toggleFilters();

    void applyFilter();

}
