package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;

public interface ProductListNavigator {

    void handleErrorService(Throwable throwable);

    void showAlert(String message);

    void showProducts(OrderedRealmCollection<Product> result);

    void showEmptyProducts();

    void toggleSortOptions();

    void toggleFilters();

    void applyFilter();

    void removeFragment(String tag);

    void updatedFilterCount();

    void clearProductsLoaded_Done();

    void clearFragment();

    void onFragmentClosed();

    void reApplyFilter();

    void showLoading();

    void closeLoading();

    void fetchProductsNew();

    void fetchProducts();

    void fetchMore();

    void setUpRecyclerViewGrid(RecyclerView view);

    void openCategoriesSelection();

    void applyCategoriesSelected(String subsId);

    void onFavoriteClick(ProductItemViewModel viewModel);

    Bundle getBundle();
}
