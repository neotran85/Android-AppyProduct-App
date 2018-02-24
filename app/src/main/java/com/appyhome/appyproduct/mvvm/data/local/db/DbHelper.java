package com.appyhome.appyproduct.mvvm.data.local.db;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;


public interface DbHelper {
    Flowable<User> createNewUser();
    Flowable<User> updateUserInfo(String phoneNumber, String token);
    void closeDatabase();
}
