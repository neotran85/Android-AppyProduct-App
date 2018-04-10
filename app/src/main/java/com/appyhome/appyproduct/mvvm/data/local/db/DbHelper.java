package com.appyhome.appyproduct.mvvm.data.local.db;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCached;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductCartResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductFavoriteResponse;

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

    Flowable<Boolean> addProducts(RealmList<Product> list);

    Flowable<RealmResults<Product>> getProductsBySubCategory(int idSub);

    Flowable<Product> getProductById(int idProduct);

    Flowable<ProductCart> addProductToCart(String userId, int productId, String variantModelId, int amountAdded);

    Flowable<RealmResults<ProductCart>> getAllProductCarts(String userId);

    Flowable<ProductCart> updateProductCartItem(long idProductCart, boolean checked, int amount, String variantModelId);

    Flowable<Boolean> removeProductCartItem(long idProductCart);

    Flowable<Boolean> emptyProductCarts(String userId);

    Flowable<RealmResults<Address>> getAllShippingAddress(String userId);

    Flowable<Boolean> addShippingAddress(String userId, String placeId, String name, String phoneNumber, String addressStr, boolean isDefault);

    Flowable<Boolean> setDefaultShippingAddress(String userId, long id);

    Flowable<Address> getDefaultShippingAddress(String userId);

    Flowable<RealmResults<ProductCart>> getAllCheckedProductCarts(String userId);

    Flowable<ProductOrder> addOrder(RealmResults<ProductCart> items,
                               String paymentMethod, Address shippingAddress,
                               String customerId, String customerName,
                               float totalCost, float discount);

    Flowable<Integer> getTotalCountProductCarts(String userId);

    Flowable<Boolean> addOrRemoveFavorite(int productId, int variantId, String userId);

    Flowable<Boolean> emptyFavorites(String userId);

    Flowable<RealmResults<ProductFavorite>> getAllProductFavorites(String userId);

    Flowable<Boolean> isFavorite(int productId, String userId);

    Flowable<RealmList<Product>> getAllProductsFavorited(String userId, ArrayList<Integer> ids);

    Flowable<ProductFilter> saveProductFilter(String userId, String shippingFrom, String discount, float rating, String priceMin, String priceMax);

    Flowable<ProductFilter> getCurrentFilter(String userId);

    Flowable<RealmResults<Product>> getAllProductsFilter(String userId);

    Flowable<Boolean> clearProductsLoaded();

    Flowable<Boolean> addProductsCached(RealmList<ProductCached> list);

    Flowable<ProductCached> getProductCachedById(int idProduct);

    Flowable<ProductCart> getProductCart(String userId, int productId);

    Flowable<RealmResults<SearchItem>> getSearchSuggestions();

    Flowable<ProductOrder> getOrderById(String userId, long orderId);

    Flowable<RealmResults<SearchItem>> getSearchHistory(String userId);

    Flowable<Boolean> addSearchItems(ArrayList<SearchItem> items);

    Flowable<Boolean> addSearchItem(SearchItem item);

    Flowable<Boolean> clearSearchHistory(String userId);

    Flowable<Boolean> addProductVariants(RealmList<ProductVariant> variants);

    Flowable<RealmResults<ProductVariant>> getProductVariants(int productId);

    Flowable<RealmResults<ProductSub>> getProductCategoryIdsByTopic(int idTopic);

    Flowable<Boolean> isProductFavorite(String userId, int idProduct);

    Flowable<Boolean> updateAllProductCarts(String userId, ArrayList<ProductCartResponse> array);

    Flowable<Boolean> updateAllProductFavorite(String userId, ArrayList<ProductFavoriteResponse> array);
}
