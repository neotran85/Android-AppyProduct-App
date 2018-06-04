package com.appyhome.appyproduct.mvvm.data.local.db;


import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Seller;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductCartResponse;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.realm.RealmList;
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

    Flowable<Boolean> addProducts(String userId, RealmList<Product> list);

    Flowable<RealmResults<Product>> getProductsBySubCategory(int idSub);

    Flowable<Product> getProductById(int idProduct);

    Flowable<RealmResults<ProductCart>> getAllProductCarts(String userId);

    Flowable<ProductCart> updateProductCartItem(long idProductCart, boolean checked, int amount, String variantModelId);

    Flowable<Boolean> removeProductCartItem(long idProductCart);

    Flowable<Boolean> emptyProductCarts(String userId);

    Flowable<RealmResults<AppyAddress>> getAllShippingAddress(String userId);

    Flowable<Boolean> setDefaultShippingAddress(String userId, long id);

    Flowable<AppyAddress> getShippingAddress(String userId, long id);

    Flowable<Boolean> removeShippingAddress(String userId, long id);

    Flowable<AppyAddress> getDefaultShippingAddress(String userId);

    Flowable<RealmResults<ProductCart>> getAllCheckedProductCarts(String userId);

    Flowable<ProductOrder> addOrder(RealmResults<ProductCart> items,
                                    String paymentMethod, AppyAddress shippingAddress,
                                    String customerId, String customerName,
                                    float totalCost, float discount, long orderId);

    Flowable<Integer> getTotalCountProductCarts(String userId);

    Flowable<Boolean> addOrRemoveFavorite(long productId, String userId);

    Flowable<Boolean> emptyFavorites(String userId);

    Flowable<RealmResults<Product>> getAllProductFavorites(String userId);

    Flowable<Boolean> isProductFavorite(String userId, long productId);

    Flowable<ProductFilter> saveProductFilter(String userId, String shippingFrom, String discount, float rating, String priceMin, String priceMax);

    Flowable<ProductFilter> getCurrentFilter(String userId);

    Flowable<RealmResults<Product>> getAllProductsFilter(String userId);

    Flowable<Boolean> clearProductsLoaded();

    Flowable<Product> getProductCachedById(long idProduct);

    Flowable<ProductCart> getProductCart(String userId, long productId, long variantId);

    Flowable<RealmResults<SearchItem>> getSearchSuggestions();

    Flowable<ProductOrder> getOrderById(String userId, long orderId);

    Flowable<RealmResults<SearchItem>> getSearchHistory(String userId);

    Flowable<Boolean> addSearchItems(ArrayList<SearchItem> items);

    Flowable<Boolean> addSearchItem(SearchItem item);

    Flowable<Boolean> clearSearchHistory(String userId);

    Flowable<Boolean> addProductVariants(RealmList<ProductVariant> variants);

    Flowable<RealmResults<ProductVariant>> getProductVariants(int productId);

    Flowable<RealmResults<ProductSub>> getProductCategoryIdsByTopic(int idTopic);

    Flowable<Boolean> syncAllProductCarts(String userId, ArrayList<ProductCartResponse> array);

    Flowable<Boolean> syncAllShippingAddresses(String userId, RealmList<AppyAddress> addresses);

    Flowable<Boolean> syncAllProductFavorite(String userId, ArrayList<Product> array);

    Flowable<ProductVariant> getProductVariantById(String variantModelId);

    Flowable<Boolean> addSeller(Seller seller);

    Flowable<Boolean> updateProductCartShippingFee(String userId, Bundle dataFees);

    Flowable<RealmResults<Product>> getWishListPadding(String userId);

    Flowable<RealmResults<Product>> getCartPadding(String userId);

    Flowable<Boolean> addProducts(String userId, RealmList<Product> list, String tag);
}
