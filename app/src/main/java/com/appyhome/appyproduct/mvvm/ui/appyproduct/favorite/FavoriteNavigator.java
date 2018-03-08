package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;

import io.realm.RealmResults;

public interface FavoriteNavigator {
    void showProducts(RealmResults<Product> result);
}
