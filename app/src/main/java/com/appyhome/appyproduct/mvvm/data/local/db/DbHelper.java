package com.appyhome.appyproduct.mvvm.data.local.db;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;

import java.util.ArrayList;

import io.reactivex.Flowable;


public interface DbHelper {
    Flowable<User> createNewUser();

    Flowable<User> updateUserInfo(String phoneNumber, String token);

    Flowable<Boolean> addProductCategories(ArrayList<ProductCategory> categories);

    Flowable<Boolean> addProductTopics(ArrayList<ProductTopic> topics);

    Flowable<Boolean> addProductSubs(ArrayList<ProductSub> subs);

    void closeDatabase();
}
