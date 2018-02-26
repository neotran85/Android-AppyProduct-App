package com.appyhome.appyproduct.mvvm.data.local.db;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;

import io.reactivex.Flowable;


public interface DbHelper {
    Flowable<User> createNewUser();
    Flowable<User> updateUserInfo(String phoneNumber, String token);
    void closeDatabase();
}
