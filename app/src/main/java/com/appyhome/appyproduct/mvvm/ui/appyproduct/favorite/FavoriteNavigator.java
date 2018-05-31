package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;

import io.realm.RealmResults;

public interface FavoriteNavigator extends FetchUserInfoNavigator {

    void showProducts(RealmResults<Product> result);

    void emptyFavorites();

    void addToCart(ProductItemViewModel viewModel);

}
