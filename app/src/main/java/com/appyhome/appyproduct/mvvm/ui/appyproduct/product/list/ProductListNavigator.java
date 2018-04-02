package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;

import java.util.ArrayList;

import io.realm.RealmResults;

public interface ProductListNavigator {
    void handleErrorService(Throwable throwable);

    void showAlert(String message);

    void showProducts(RealmResults<Product> result);

    void showProducts(Product[] list);

    void getAllFavorites_Done(ArrayList<Integer> listId);

    void showEmptyProducts();

    void toggleSortOptions();

    void toggleFilters();

    void applyFilter();

    void removeFragment(String tag);

    void updatedFilterCount();

    void clearFragment();

    void onFragmentClosed();

}
