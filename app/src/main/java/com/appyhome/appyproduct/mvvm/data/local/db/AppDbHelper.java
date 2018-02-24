package com.appyhome.appyproduct.mvvm.data.local.db;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


@Singleton
public class AppDbHelper implements DbHelper {

    private final Realm mRealm;

    @Inject
    public AppDbHelper(Realm realm) {
        this.mRealm = realm;
    }

    @Override
    public Flowable<User> getUserByPhoneNumber(final String phoneNumber) {
        User result = mRealm.where(User.class)
                .equalTo("phoneNumber", phoneNumber)
                .findFirst();
        if (result == null) {
            return createNewUser();
        } else {
            return result.asFlowable();
        }
    }

    @Override
    public Flowable<User> createNewUser() {
        User person = mRealm.createObject(User.class);
        return person.asFlowable();
    }

}
