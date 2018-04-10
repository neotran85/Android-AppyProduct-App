package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCached;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;

import io.realm.RealmResults;

public interface FavoriteNavigator {
    void showProducts(RealmResults<ProductFavorite> result);
    void emptyFavorites();
}
