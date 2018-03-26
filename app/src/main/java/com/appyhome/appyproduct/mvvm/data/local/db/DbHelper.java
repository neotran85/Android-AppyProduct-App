package com.appyhome.appyproduct.mvvm.data.local.db;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCached;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.realm.RealmResults;


public interface DbHelper {
    Flowable<User> createNewUser();

    Flowable<User> updateUserInfo(String phoneNumber, String token);

    Flowable<Boolean> addProductCategories(ArrayList<ProductCategory> categories);

    Flowable<Boolean> addProductTopics(ArrayList<ProductTopic> topics);

    Flowable<Boolean> addProductSubs(ArrayList<ProductSub> subs);

    Flowable<RealmResults<ProductTopic>> getAllProductTopics();

    Flowable<RealmResults<ProductCategory>> getProductCategoriesByTopic(int idTopic);

    Flowable<RealmResults<ProductSub>> getSubProductCategoryByCategory(int idCategory);

    Flowable<ProductTopic> getProductTopicById(int idTopic);

    Flowable<Boolean> addProducts(Product[] list);

    Flowable<RealmResults<Product>> getProductsBySubCategory(int idSub);

    Flowable<Product> getProductById(int idProduct);

    Flowable<ProductCart> addProductToCart(String userId, int productId, int amountAdded);

    Flowable<RealmResults<ProductCart>> getAllProductCarts(String userId);

    Flowable<Boolean> updateProductCartItem(long idProductCart, boolean checked, int amount);

    Flowable<Boolean> removeProductCartItem(long idProductCart);

    Flowable<Boolean> emptyProductCarts(String userId);

    Flowable<RealmResults<Address>> getAllShippingAddress(String userId);

    Flowable<Boolean> addShippingAddress(String userId, String placeId, String name, String phoneNumber, String addressStr, boolean isDefault);

    Flowable<Boolean> setDefaultShippingAddress(String userId, long id);

    Flowable<Address> getDefaultShippingAddress(String userId);

    Flowable<RealmResults<ProductCart>> getAllCheckedProductCarts(String userId);

    Flowable<Boolean> addOrder(RealmResults<ProductCart> items,
                               String paymentMethod, Address shippingAddress,
                               String customerId, String customerName,
                               float totalCost, float discount);

    Flowable<Integer> getTotalCountProductCarts(String userId);

    Flowable<Boolean> addOrRemoveFavorite(int productId, String userId);

    Flowable<RealmResults<ProductFavorite>> getAllProductFavorites(String userId);

    Flowable<Boolean> isFavorite(int productId, String userId);

    Flowable<RealmResults<ProductCached>> getAllProductsFavorited(ArrayList<Integer> ids);

    void closeDatabase();

    Flowable<ProductFilter> saveProductFilter(String userId, String shippingFrom, String discount, float rating, String priceMin, String priceMax);

    Flowable<ProductFilter> getCurrentFilter(String userId);

    Flowable<RealmResults<Product>> getAllProductsFilter(String userId, int idSubCategory);

    Flowable<Boolean> clearProductsLoaded();

    Flowable<Boolean> addProductsCached(ProductCached[] list);

    Flowable<ProductCached> getProductCachedById(int idProduct);
}
