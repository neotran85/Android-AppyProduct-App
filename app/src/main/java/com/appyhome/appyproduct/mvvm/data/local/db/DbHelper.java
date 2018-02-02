package com.appyhome.appyproduct.mvvm.data.local.db;

import com.appyhome.appyproduct.mvvm.data.model.db.User;

import java.util.List;

import io.reactivex.Observable;


public interface DbHelper {

    Observable<Boolean> insertUser(final User user);
    Observable<List<User>> getAllUsers();
}
