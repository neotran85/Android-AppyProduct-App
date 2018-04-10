package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCached;

import io.realm.RealmResults;

public interface FavoriteNavigator {
    void showProducts(Product[] result);
    void emptyFavorites();
}
