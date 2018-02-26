package com.appyhome.appyproduct.mvvm.data.local.db;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.realm.Realm;


@Singleton
public class AppDbHelper implements DbHelper {

    private Realm mRealm;

    @Inject
    public AppDbHelper(Realm realm) {
        this.mRealm = realm;
    }

    @Override
    public Flowable<User> updateUserInfo(final String phoneNumber, String token) {
        getRealm().beginTransaction();
        User result = getRealm().where(User.class)
                .equalTo("phoneNumber", phoneNumber)
                .findFirstAsync();
        if (result == null || !result.isValid()) {
            User person = getRealm().createObject(User.class, phoneNumber);
            person.setPhoneNumber(phoneNumber);
            person.setToken(token);
            person = getRealm().copyToRealmOrUpdate(person);
            getRealm().commitTransaction();
            return person.asFlowable();
        } else {
            result.setToken(token);
            result = getRealm().copyToRealmOrUpdate(result);
            getRealm().commitTransaction();
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
