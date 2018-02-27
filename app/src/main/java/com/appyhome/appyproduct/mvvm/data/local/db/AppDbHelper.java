package com.appyhome.appyproduct.mvvm.data.local.db;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


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
            person.phoneNumber = phoneNumber;
            person.token = token;
            person = getRealm().copyToRealmOrUpdate(person);
            getRealm().commitTransaction();
            return person.asFlowable();
        } else {
            result.token = token;
            result = getRealm().copyToRealmOrUpdate(result);
            getRealm().commitTransaction();
            return result.asFlowable();
        }
    }

    @Override
    public Flowable<RealmResults<ProductCategory>> getProductCategoryByTopic(int idTopic) {
        getRealm().beginTransaction();
        Flowable<RealmResults<ProductCategory>> categories = getRealm().where(ProductCategory.class)
                .equalTo("id_topic", idTopic)
                .findAllAsync().asFlowable();
        getRealm().commitTransaction();
        return categories;
    }

    @Override
    public Flowable<RealmResults<ProductTopic>> getAllProductTopics() {
        getRealm().beginTransaction();
        Flowable<RealmResults<ProductTopic>> topcis = getRealm().where(ProductTopic.class).findAllAsync().asFlowable();
        getRealm().commitTransaction();
        return topcis;
    }

    @Override
    public Flowable<Boolean> addProductCategories(ArrayList<ProductCategory> categories) {
        try {
            getRealm().beginTransaction();
            for (ProductCategory category : categories) {
                getRealm().copyToRealmOrUpdate(category);
            }
            getRealm().commitTransaction();
            return Flowable.just(true);
        } catch (Exception e) {
            return Flowable.just(false);
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


    @Override
    public Flowable<Boolean> addProductSubs(ArrayList<ProductSub> items) {
        try {
            getRealm().beginTransaction();
            for (ProductSub item : items) {
                getRealm().copyToRealmOrUpdate(item);
            }
            getRealm().commitTransaction();
            return Flowable.just(true);
        } catch (Exception e) {
            return Flowable.just(false);
        }
    }

    @Override
    public Flowable<Boolean> addProductTopics(ArrayList<ProductTopic> topics) {
        try {
            getRealm().beginTransaction();
            for (ProductTopic topic : topics) {
                getRealm().copyToRealmOrUpdate(topic);
            }
            getRealm().commitTransaction();
            return Flowable.just(true);
        } catch (Exception e) {
            return Flowable.just(false);
        }
    }

}
