package com.appyhome.appyproduct.mvvm.data.local.db;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.realm.Realm;
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
    public Flowable<RealmResults<ProductSub>> getSubProductCategoryByCategory(int idCategory) {
        getRealm().beginTransaction();
        Flowable<RealmResults<ProductSub>> subs = getRealm().where(ProductSub.class)
                .equalTo("id_category", idCategory)
                .findAllAsync().asFlowable();
        getRealm().commitTransaction();
        return subs;
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

    @Override
    public Flowable<ProductTopic> getProductTopicById(int idTopic) {
        getRealm().beginTransaction();
        Flowable<ProductTopic> topic = getRealm().where(ProductTopic.class)
                .equalTo("id", idTopic)
                .findFirstAsync().asFlowable();
        getRealm().commitTransaction();
        return topic;
    }

    @Override
    public Flowable<Product> getProductById(int idProduct) {
        getRealm().beginTransaction();
        Flowable<Product> topic = getRealm().where(Product.class)
                .equalTo("id", idProduct)
                .findFirstAsync().asFlowable();
        getRealm().commitTransaction();
        return topic;
    }

    @Override
    public Flowable<RealmResults<Product>> getProductsBySubCategory(int idSub) {
        getRealm().beginTransaction();
        Flowable<RealmResults<Product>> topic = getRealm().where(Product.class)
                .equalTo("category_id", idSub)
                .findAllAsync().asFlowable();
        getRealm().commitTransaction();
        return topic;
    }


    @Override
    public Flowable<Boolean> addProducts(Product[] list) {
        try {
            getRealm().beginTransaction();
            for (Product product : list) {
                getRealm().copyToRealmOrUpdate(product);
            }
            getRealm().commitTransaction();
            return Flowable.just(true);
        } catch (Exception e) {
            return Flowable.just(false);
        }
    }

    /******* PRODUCT CART METHODS *******/

    @Override
    public Flowable<RealmResults<ProductCart>> getAllProductCarts(String userId) {
        getRealm().beginTransaction();
        Flowable<RealmResults<ProductCart>> carts = getRealm().where(ProductCart.class)
                .equalTo("user_id", userId)
                .sort("seller_name")
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return carts;
    }

    @Override
    public Flowable<Boolean> productCartUpdate(long idProductCart, boolean checked, int amount) {
        try {
            getRealm().beginTransaction();
            ProductCart productCart = getRealm().where(ProductCart.class)
                    .equalTo("id", idProductCart)
                    .findFirst();
            productCart.checked = checked;
            productCart.amount = amount;
            getRealm().copyToRealmOrUpdate(productCart);
            getRealm().commitTransaction();
            return Flowable.just(true);
        } catch (Exception e) {
            return Flowable.just(false);
        }
    }

    private String[] storeName = {"Store 1", "Store 2", "Store 3", "Store 4"};

    private ProductCart createNewProductCart(Product product, String userId) {
        ProductCart cartItem = new ProductCart();
        cartItem.id = System.currentTimeMillis();
        cartItem.product_id = product.id;
        cartItem.seller_id = product.seller_id;
        int randomNum = new Random().nextInt(storeName.length);
        cartItem.seller_name = storeName[randomNum];
        cartItem.product_name = product.product_name;
        cartItem.amount = 0;
        cartItem.checked = true;
        cartItem.product_avatar = product.avatar_name;
        cartItem.user_id = userId;
        return cartItem;
    }

    @Override
    public Flowable<ProductCart> addProductToCart(Product product, String userId) {
        try {
            getRealm().beginTransaction();
            ProductCart productCart = getRealm().where(ProductCart.class)
                    .equalTo("product_id", product.id)
                    .findFirst();
            if (productCart == null) {
                productCart = createNewProductCart(product, userId);
            }
            productCart.amount = productCart.amount + 1;
            productCart = getRealm().copyToRealmOrUpdate(productCart);
            getRealm().commitTransaction();
            return Flowable.just(productCart);
        } catch (Exception e) {
            e.printStackTrace();
            return Flowable.just(null);
        }
    }
}
