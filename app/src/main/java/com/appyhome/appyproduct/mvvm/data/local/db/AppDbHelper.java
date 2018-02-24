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

    private Realm mRealm;

    @Inject
    public AppDbHelper(Realm realm) {
        this.mRealm = realm;
    }

    @Override
    public Flowable<User> getUserByPhoneNumber(final String phoneNumber) {
        User result = getRealm().where(User.class)
                .equalTo("phoneNumber", phoneNumber)
                .findFirstAsync();
        if (result == null) {
            User person = getRealm().createObject(User.class);
            person.setPhoneNumber(phoneNumber);
            getRealm().beginTransaction();
            getRealm().copyToRealmOrUpdate(person);
            getRealm().commitTransaction();
            return person.asFlowable();
        } else {
            return result.asFlowable();
        }
    }

    @Override
    public void closeDatabase() {
        if (mRealm != null) mRealm.close();
    }

    private Realm getRealm() {
        if (mRealm == null || mRealm.isClosed())
            mRealm = Realm.getDefaultInstance();
        return mRealm;
    }

    @Override
    public Flowable<User> createNewUser() {
        User person = getRealm().createObject(User.class);
        return person.asFlowable();
    }

}
