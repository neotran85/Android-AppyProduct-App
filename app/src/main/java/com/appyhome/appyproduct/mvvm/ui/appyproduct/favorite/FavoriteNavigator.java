package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;

import io.realm.RealmResults;

public interface FavoriteNavigator extends FetchUserInfoNavigator {
    void showProducts(RealmResults<ProductFavorite> result);

    void emptyFavorites();
}
