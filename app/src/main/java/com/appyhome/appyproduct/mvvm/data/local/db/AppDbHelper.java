package com.appyhome.appyproduct.mvvm.data.local.db;

import android.os.Bundle;
import android.text.TextUtils;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
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

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;


@Singleton
public class AppDbHelper implements DbHelper {
    private Realm mRealm;

    @Inject
    public AppDbHelper(Realm realm) {
        this.mRealm = realm;
    }

    @Override
    public Flowable<User> updateUserInfo(final String phoneNumber, String token) {
        beginTransaction();
        User result = getRealm().where(User.class)
                .equalTo("phoneNumber", phoneNumber)
                .findFirst();
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
        beginTransaction();
        Flowable<RealmResults<ProductSub>> subs = getRealm().where(ProductSub.class)
                .equalTo("id_category", idCategory)
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return subs;
    }

    @Override
    public Flowable<RealmResults<ProductCategory>> getProductCategoriesByTopic(int idTopic) {
        beginTransaction();
        Flowable<RealmResults<ProductCategory>> categories = getRealm().where(ProductCategory.class)
                .equalTo("id_topic", idTopic)
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return categories;
    }

    @Override
    public Flowable<RealmResults<ProductSub>> getProductCategoryIdsByTopic(int idTopic) {
        beginTransaction();
        RealmResults<ProductCategory> categories = getRealm().where(ProductCategory.class)
                .equalTo("id_topic", idTopic)
                .findAll();

        RealmQuery<ProductSub> query = getRealm().where(ProductSub.class);
        if (categories.size() > 0) {
            int index = 0;
            for (ProductCategory item : categories) {
                if (index++ > 0) {
                    query = query.or();
                }
                query = query.equalTo("id_category", item.id);
            }
        }

        Flowable<RealmResults<ProductSub>> result = query.findAll().asFlowable();

        getRealm().commitTransaction();
        return result;
    }


    @Override
    public Flowable<RealmResults<ProductTopic>> getAllProductTopics() {
        beginTransaction();
        Flowable<RealmResults<ProductTopic>> topics = getRealm().where(ProductTopic.class).findAll().asFlowable();
        getRealm().commitTransaction();
        return topics;
    }

    @Override
    public Flowable<Boolean> addProductCategories(ArrayList<ProductCategory> categories) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                for (ProductCategory cat : categories) {
                    String idSubsString = "";
                    RealmResults<ProductSub> subs = getRealm().where(ProductSub.class)
                            .equalTo("id_category", cat.id)
                            .findAll();
                    if (subs != null && subs.size() > 0) {
                        ArrayList<String> idSubs = new ArrayList<>();
                        for (ProductSub sub : subs) {
                            idSubs.add(sub.id + "");
                        }
                        idSubsString = TextUtils.join(",", idSubs);
                        cat.thumbnail = subs.get(0).thumbnail;
                    }
                    cat.sub_ids = idSubsString;
                }
                getRealm().copyToRealmOrUpdate(categories);
                getRealm().commitTransaction();
                return true;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }

    @Override
    public Flowable<RealmResults<ProductVariant>> getProductVariants(int productId) {
        beginTransaction();
        Flowable<RealmResults<ProductVariant>> variants = getRealm().where(ProductVariant.class)
                .equalTo("product_id", productId)
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return variants;
    }

    @Override
    public Flowable<ProductVariant> getProductVariantById(String variantModelId) {
        beginTransaction();
        ProductVariant variant = getRealm().where(ProductVariant.class)
                .equalTo("model_id", variantModelId)
                .findFirst();
        getRealm().commitTransaction();
        return variant.asFlowable();
    }

    @Override
    public Flowable<Boolean> addProductVariants(RealmList<ProductVariant> variants) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                Product product = null;
                if (variants.get(0) != null) {
                    product = getRealm().where(Product.class)
                            .equalTo("id", variants.get(0).product_id)
                            .findFirst();
                }
                for (ProductVariant variant : variants) {
                    variant.avatar = (variant.images != null && variant.images.get(0) != null) ? variant.images.get(0).URL : "";
                    if (product != null) {
                        variant.product_name = product.product_name;
                        variant.seller_name = product.seller_name;
                        variant.seller_id = product.seller_id;
                        variant.stock_location = product.stock_location;
                        variant.country_manu = product.country_manu;
                    }
                }
                getRealm().copyToRealmOrUpdate(variants);
                getRealm().commitTransaction();
                return true;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> addSeller(Seller seller) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                getRealm().copyToRealmOrUpdate(seller);
                getRealm().commitTransaction();
                return true;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
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
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                getRealm().copyToRealmOrUpdate(items);
                getRealm().commitTransaction();
                return true;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> addProductTopics(ArrayList<ProductTopic> topics) {
        try {
            beginTransaction();
            for (ProductTopic topic : topics) {
                String idSubsString = "";
                RealmResults<ProductCategory> cats = getRealm().where(ProductCategory.class)
                        .equalTo("id_topic", topic.id)
                        .findAll();
                if (cats != null) {
                    ArrayList<String> idSubs = new ArrayList<>();
                    for (ProductCategory cat : cats) {
                        idSubs.add(cat.sub_ids);
                    }
                    idSubsString = TextUtils.join(",", idSubs);
                }
                topic.sub_ids = idSubsString;
            }
            getRealm().copyToRealmOrUpdate(topics);
            getRealm().commitTransaction();
            return Flowable.just(true);
        } catch (Exception e) {
            getRealm().cancelTransaction();
            return Flowable.just(false);
        }
    }

    @Override
    public Flowable<ProductTopic> getProductTopicById(int idTopic) {
        beginTransaction();
        Flowable<ProductTopic> topic = getRealm().where(ProductTopic.class)
                .equalTo("id", idTopic)
                .findFirst().asFlowable();
        getRealm().commitTransaction();
        return topic;
    }

    @Override
    public Flowable<Product> getProductCachedById(long idProduct) {
        beginTransaction();
        Product product = getRealm().where(Product.class)
                .equalTo("id", idProduct)
                .findFirst();
        getRealm().commitTransaction();
        return product != null ? product.asFlowable() : null;
    }

    @Override
    public Flowable<Product> getProductById(int idProduct) {
        beginTransaction();
        Product product = getRealm().where(Product.class)
                .equalTo("id", idProduct)
                .findFirst();
        if (product == null)
            product = new Product();
        getRealm().commitTransaction();
        return product.asFlowable();
    }

    @Override
    public Flowable<RealmResults<Product>> getProductsBySubCategory(int idSub) {
        beginTransaction();
        Flowable<RealmResults<Product>> topic = getRealm().where(Product.class)
                .equalTo("category_id", idSub)
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return topic;
    }

    @Override
    public Flowable<Boolean> addProducts(RealmList<Product> list) {
        try {
            beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                Product product = list.get(i);
                if (product != null)
                    product.cached = i + 1;
            }
            getRealm().copyToRealmOrUpdate(list);
            getRealm().commitTransaction();
            return Flowable.just(true);
        } catch (Exception e) {
            getRealm().cancelTransaction();
            return Flowable.just(false);
        }
    }

    /******* PRODUCT CART METHODS *******/

    @Override
    public Flowable<AppyAddress> getDefaultShippingAddress(String userId) {
        return Flowable.fromCallable(() -> {
            beginTransaction();
            AppyAddress address = getRealm().where(AppyAddress.class)
                    .equalTo("user_id", userId)
                    .equalTo("is_default", 1)
                    .findFirst();
            getRealm().commitTransaction();
            return address;
        });
    }

    @Override
    public Flowable<RealmResults<AppyAddress>> getAllShippingAddress(String userId) {
        beginTransaction();
        String[] fieldNames = {"is_default", "id"};
        Sort sortOrder[] = {Sort.DESCENDING, Sort.DESCENDING};
        Flowable<RealmResults<AppyAddress>> addresses = getRealm().where(AppyAddress.class)
                .equalTo("user_id", userId)
                .sort(fieldNames, sortOrder)
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return addresses;
    }

    @Override
    public Flowable<RealmResults<ProductCart>> getAllProductCarts(String userId) {
        beginTransaction();
        Flowable<RealmResults<ProductCart>> carts = getRealm().where(ProductCart.class)
                .equalTo("user_id", userId)
                .equalTo("order_id", 0)
                .sort("time_added", Sort.DESCENDING)
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return carts;
    }

    private ProductCart isContaining(ArrayList<ProductCart> carts, ProductCart itemCart) {
        if (carts != null && carts.size() > 0)
            for (ProductCart item : carts) {
                if (item.cart_id == itemCart.cart_id) {
                    return item;
                }
            }
        return null;
    }

    @Override
    public Flowable<Boolean> syncAllShippingAddresses(String userId, RealmList<AppyAddress> addresses) {
        return Flowable.fromCallable(() -> {
            beginTransaction();
            for (AppyAddress address : addresses) {
                address.user_id = userId;
            }
            getRealm().copyToRealmOrUpdate(addresses);
            getRealm().commitTransaction();
            return true;
        });
    }

    @Override
    public Flowable<Boolean> syncAllProductCarts(String userId, ArrayList<ProductCartResponse> array) {
        return Flowable.fromCallable(() -> {
            beginTransaction();
            ArrayList<ProductCart> arrayList = new ArrayList<>();
            long timeAdded = System.currentTimeMillis();
            for (ProductCartResponse response : array) {
                ProductCart cartItem = new ProductCart();
                cartItem = inputProductCart(userId, cartItem, response);
                cartItem.time_added = timeAdded;
                timeAdded++;
                arrayList.add(cartItem);
            }
            RealmResults<ProductCart> carts = getRealm().where(ProductCart.class)
                    .equalTo("user_id", userId)
                    .equalTo("order_id", 0)
                    .findAll();

            if (carts != null && carts.isValid() && carts.size() > 0) {
                // REMOVED CART ITEMS NOT EXIST IN THE SERVER
                for (ProductCart item : carts) {
                    ProductCart common = isContaining(arrayList, item);
                    if (common != null) {
                        common.checked = item.checked;
                    } else {
                        item.deleteFromRealm();
                    }
                }
            }
            getRealm().copyToRealmOrUpdate(arrayList);
            getRealm().commitTransaction();
            return true;
        });
    }

    @Override
    public Flowable<Boolean> syncAllProductFavorite(String userId, ArrayList<ProductFavorite> array) {
        return Flowable.fromCallable(() -> {
            beginTransaction();
            RealmResults<ProductFavorite> favorites = getRealm().where(ProductFavorite.class)
                    .equalTo("user_id", userId)
                    .findAll();

            for (ProductFavorite item : array) {
                item.user_id = userId;
                item.setUpdated_date();
            }

            if (favorites != null)
                favorites.deleteAllFromRealm();

            ArrayList<Product> productsCached = new ArrayList<>();
            for (ProductFavorite item : array) {
                Product cachedItem = item.toProductCached();
                productsCached.add(cachedItem);
            }

            getRealm().copyToRealmOrUpdate(productsCached);
            getRealm().copyToRealmOrUpdate(array);
            getRealm().commitTransaction();
            return true;
        });
    }

    private ProductCart inputProductCart(String userId, ProductCart cartItem, ProductCartResponse item) {
        cartItem.product_image = item.product_image;
        cartItem.price = item.variant_price;
        cartItem.cart_id = item.cart_id;
        cartItem.product_id = item.product_id;
        cartItem.seller_id = item.seller_id;
        cartItem.seller_name = item.seller_name;
        cartItem.product_name = item.product_name;
        cartItem.amount = item.quantity;
        cartItem.user_id = userId;
        cartItem.order_id = 0;
        cartItem.variant_model_id = item.product_id + "_" + item.variant_id;
        cartItem.variant_id = item.variant_id;
        cartItem.variant_stock = item.variant_stock_left;
        cartItem.variant_name = item.variant_name;
        return cartItem;
    }

    @Override
    public Flowable<Integer> getTotalCountProductCarts(String userId) {
        beginTransaction();
        RealmResults<ProductCart> carts = getRealm().where(ProductCart.class)
                .equalTo("order_id", 0)
                .equalTo("user_id", userId)
                .findAll();
        int total = 0;
        if (carts != null) {
            for (ProductCart item : carts) {
                total = total + item.amount;
            }
        }
        getRealm().commitTransaction();
        return Flowable.just(total);
    }

    @Override
    public Flowable<RealmResults<ProductCart>> getAllCheckedProductCarts(String userId) {
        beginTransaction();
        Flowable<RealmResults<ProductCart>> carts = getRealm().where(ProductCart.class)
                .equalTo("user_id", userId)
                .equalTo("order_id", 0)
                .equalTo("checked", true)
                .sort("seller_name")
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return carts;
    }

    @Override
    public Flowable<Boolean> removeShippingAddress(String userId, long id) {
        beginTransaction();
        AppyAddress address = getRealm().where(AppyAddress.class)
                .equalTo("id", id)
                .equalTo("user_id", userId)
                .findFirst();
        if (address != null && address.isValid()) address.deleteFromRealm();
        getRealm().commitTransaction();
        return Flowable.just(address != null && address.isValid());
    }

    @Override
    public Flowable<AppyAddress> getShippingAddress(String userId, long id) {
        return Flowable.fromCallable(() -> {
            beginTransaction();
            AppyAddress address = getRealm().where(AppyAddress.class)
                    .equalTo("id", id)
                    .equalTo("user_id", userId)
                    .findFirst();
            getRealm().commitTransaction();
            return address;
        });
    }

    @Override
    public Flowable<Boolean> setDefaultShippingAddress(String userId, long id) {
        try {
            beginTransaction();
            AppyAddress address = getRealm().where(AppyAddress.class)
                    .equalTo("id", id)
                    .findFirst();

            RealmResults<AppyAddress> addressList = getRealm().where(AppyAddress.class)
                    .equalTo("user_id", userId)
                    .findAll();
            if (addressList != null && addressList.isValid()) {
                for (AppyAddress address1 : addressList) {
                    address1.is_default = 0;
                }
                getRealm().copyToRealmOrUpdate(addressList);
            }
            if (address != null && address.isValid()) {
                address.is_default = 1;
                address = getRealm().copyToRealmOrUpdate(address);
            }
            getRealm().commitTransaction();
            return Flowable.just(address != null && address.isValid());
        } catch (Exception e) {
            getRealm().cancelTransaction();
            return Flowable.just(false);
        }
    }

    @Override
    public Flowable<Boolean> removeProductCartItem(long idProductCart) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                getRealm().where(ProductCart.class)
                        .equalTo("id", idProductCart)
                        .findFirst().deleteFromRealm();
                getRealm().commitTransaction();
                return true;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> updateProductCartShippingFee(String userId, Bundle dataFees) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                RealmResults<ProductCart> cart = getRealm().where(ProductCart.class)
                        .equalTo("user_id", userId)
                        .equalTo("checked", true)
                        .findAll();
                for (ProductCart item : cart) {
                    if (dataFees.containsKey(item.seller_id + "")) {
                        item.shipping_fee = dataFees.getString(item.seller_id + "");
                    } else item.shipping_fee = "";
                }
                getRealm().copyToRealmOrUpdate(cart);
                getRealm().commitTransaction();
                return true;
            } catch (Exception e) {
                getRealm().cancelTransaction();
            }
            return false;
        });
    }

    @Override
    public Flowable<ProductCart> updateProductCartItem(long idProductCart, boolean checked, int amount, String variantModelId) {
        try {
            beginTransaction();
            ProductCart productCart = getRealm().where(ProductCart.class)
                    .equalTo("cart_id", idProductCart)
                    .findFirst();
            if (productCart != null) {
                productCart.checked = checked;
                if (amount > 0)
                    productCart.amount = amount;
                if (variantModelId.length() > 0) {
                    ProductVariant variant = getRealm().where(ProductVariant.class)
                            .equalTo("model_id", variantModelId).findFirst();
                    if (variant != null && variant.isValid()) {
                        productCart.variant_name = variant.variant_name;
                        productCart.variant_id = variant.id;
                        productCart.variant_model_id = variantModelId;
                        productCart.variant_stock = variant.quantity;
                        productCart.price = variant.price;
                        productCart.product_image = variant.avatar;
                    }
                }
                ProductCart existedProductCart = getRealm().where(ProductCart.class)
                        .equalTo("user_id", productCart.user_id)
                        .notEqualTo("cart_id", idProductCart)
                        .equalTo("variant_model_id", variantModelId)
                        .findFirst();
                if (existedProductCart != null && existedProductCart.isValid()) {
                    productCart.amount = existedProductCart.amount + amount;
                    existedProductCart.deleteFromRealm();
                }
                productCart = getRealm().copyToRealmOrUpdate(productCart);
            }
            getRealm().commitTransaction();
            return productCart != null ? productCart.asFlowable() : Flowable.empty();
        } catch (Exception e) {
            getRealm().cancelTransaction();
            return Flowable.empty();
        }
    }

    @Override
    public Flowable<ProductCart> getProductCart(String userId, long productId, long variantId) {
        try {
            beginTransaction();
            ProductCart productCart = getRealm().where(ProductCart.class)
                    .equalTo("product_id", productId)
                    .equalTo("user_id", userId)
                    .equalTo("variant_id", variantId)
                    .equalTo("order_id", 0)
                    .findFirst();
            getRealm().commitTransaction();
            return productCart.asFlowable();
        } catch (Exception e) {
            getRealm().cancelTransaction();
            e.printStackTrace();
            return new ProductCart().asFlowable();
        }
    }

    @Override
    public Flowable<Boolean> emptyProductCarts(String userId) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                RealmResults<ProductCart> carts = getRealm().where(ProductCart.class)
                        .equalTo("user_id", userId)
                        .equalTo("order_id", 0)
                        .findAll();
                carts.deleteAllFromRealm();
                getRealm().commitTransaction();
                return true;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    public Flowable<ProductOrder> addOrder(RealmResults<ProductCart> items,
                                           String paymentMethod, AppyAddress shippingAddress,
                                           String customerId, String customerName,
                                           float totalCost, float discount, long orderId) {
        try {
            beginTransaction();
            ProductOrder order = new ProductOrder();
            order.id = orderId == 0 ? System.currentTimeMillis() : orderId;
            order.customer_name = customerName;
            order.discount = discount;
            order.payment_method = paymentMethod;

            getRealm().copyToRealmOrUpdate(items);
            order = getRealm().copyToRealmOrUpdate(order);
            getRealm().commitTransaction();
            return order.asFlowable();
        } catch (Exception e) {
            getRealm().cancelTransaction();
            return new ProductOrder().asFlowable();
        }
    }

    @Override
    public Flowable<ProductFilter> getCurrentFilter(String userId) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                ProductFilter filter = getRealm().where(ProductFilter.class)
                        .equalTo("user_id", userId)
                        .findFirst();
                getRealm().commitTransaction();
                return filter != null ? filter : new ProductFilter();
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return new ProductFilter();
            }
        });
    }

    @Override
    public Flowable<ProductFilter> saveProductFilter(String userId, String shippingFrom, String
            discount, float rating, String priceMin, String priceMax) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                ProductFilter filter = getRealm().where(ProductFilter.class)
                        .equalTo("user_id", userId)
                        .findFirst();
                if (filter == null || !filter.isValid()) {
                    filter = new ProductFilter();
                    filter.id = System.currentTimeMillis();
                    filter.user_id = userId;
                }
                filter.shipping_from = shippingFrom;
                filter.discount = discount;
                filter.rating = rating;

                filter.price_min = (priceMin.length() > 0) ? Float.valueOf(priceMin) : -1;
                filter.price_max = (priceMax.length() > 0) ? Float.valueOf(priceMax) : -1;

                filter = getRealm().copyToRealmOrUpdate(filter);
                getRealm().commitTransaction();
                return filter;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return null;
            }
        });
    }

    @Override
    public Flowable<Boolean> emptyFavorites(String userId) {
        return Flowable.fromCallable(() -> {
            try {
                Boolean value;
                beginTransaction();
                RealmResults<ProductFavorite> favorites = getRealm().where(ProductFavorite.class)
                        .equalTo("user_id", userId).findAll();

                if (favorites == null || favorites.size() > 0) {
                    favorites.deleteAllFromRealm();
                    value = true;
                } else {
                    value = false;
                }
                getRealm().commitTransaction();
                return value;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }


    @Override
    public Flowable<Boolean> addOrRemoveFavorite(long productId, String userId) {
        return Flowable.fromCallable(() -> {
            try {
                Boolean value = false;
                beginTransaction();

                Product product = getRealm().where(Product.class)
                        .equalTo("id", productId)
                        .findFirst();

                ProductFavorite favorite = getRealm().where(ProductFavorite.class)
                        .equalTo("user_id", userId)
                        .equalTo("id", productId).findFirst();

                if (favorite != null && favorite.isValid()) {
                    favorite.deleteFromRealm();
                    value = false;
                } else {
                    favorite = new ProductFavorite(userId, product);
                    getRealm().copyToRealmOrUpdate(favorite);
                    value = true;
                }
                getRealm().commitTransaction();
                return value;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }

    private void beginTransaction() {
        if (getRealm().isInTransaction()) {
            getRealm().commitTransaction();
        }
        getRealm().beginTransaction();
    }

    @Override
    public Flowable<Boolean> isProductFavorite(String userId, long productId) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                RealmQuery<ProductFavorite> query = getRealm().where(ProductFavorite.class)
                        .equalTo("user_id", userId)
                        .equalTo("id", productId);
                ProductFavorite favorite = query.findFirst();
                boolean isFavorite = (favorite != null && favorite.isValid());
                getRealm().commitTransaction();
                return isFavorite;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> clearProductsLoaded() {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                boolean success = getRealm().where(Product.class)
                        .greaterThan("cached", 0)
                        .findAll().deleteAllFromRealm();
                getRealm().commitTransaction();
                return success;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }

    @Override
    public Flowable<RealmResults<Product>> getAllProductsFilter(String userId) {
        beginTransaction();
        ProductFilter filter = getRealm().where(ProductFilter.class)
                .equalTo("user_id", userId)
                .findFirst();
        RealmQuery query = getRealm().where(Product.class);
        query = query.greaterThan("cached", 0);
        if (filter != null) {
            if (filter.shipping_from != null && filter.shipping_from.length() > 0) {
                if (filter.shipping_from.equals("Local"))
                    query = query.equalTo("stock_location", "MY");
                else
                    query = query.notEqualTo("stock_location", "MY");
            }
            double min = filter.price_min > 0 ? filter.price_min : 0;
            double max = filter.price_max > 0 ? filter.price_max : 1000000000;
            query = query.between("lowest_price", min, max);
            if (filter.rating >= 0)
                query = query.greaterThanOrEqualTo("rate", filter.rating);
        }
        RealmResults<Product> result = (query != null) ? query.findAll() : null;
        result = (result != null) ? result.sort("cached", Sort.ASCENDING) : null;
        getRealm().commitTransaction();
        return (result != null) ? result.asFlowable() : null;
    }

    @Override
    public Flowable<RealmResults<ProductFavorite>> getAllProductFavorites(String userId) {
        beginTransaction();
        Flowable<RealmResults<ProductFavorite>> favorites = getRealm().where(ProductFavorite.class)
                .equalTo("user_id", userId)
                .sort("updated_date", Sort.DESCENDING)
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return favorites;
    }

    @Override
    public Flowable<ProductOrder> getOrderById(String userId, long orderId) {
        beginTransaction();
        Flowable<ProductOrder> order = getRealm().where(ProductOrder.class)
                .equalTo("customer_id", userId)
                .equalTo("id", orderId)
                .findFirst().asFlowable();
        getRealm().commitTransaction();
        return order;
    }

    @Override
    public Flowable<RealmResults<SearchItem>> getSearchHistory(String userId) {
        beginTransaction();
        Flowable<RealmResults<SearchItem>> result = getRealm().where(SearchItem.class)
                .isNotNull("content")
                .equalTo("user_id", userId)
                .equalTo("cached", true)
                .notEqualTo("content", "")
                .sort("time_added", Sort.DESCENDING)
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return result;
    }

    @Override
    public Flowable<RealmResults<SearchItem>> getSearchSuggestions() {
        beginTransaction();
        Flowable<RealmResults<SearchItem>> result = getRealm().where(SearchItem.class)
                .isNotNull("content")
                .equalTo("cached", true)
                .notEqualTo("content", "")
                .findAll().asFlowable();
        getRealm().commitTransaction();
        return result;
    }

    @Override
    public Flowable<Boolean> clearSearchHistory(String userId) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                boolean success = getRealm().where(SearchItem.class)
                        .equalTo("user_id", userId)
                        .equalTo("cached", true)
                        .findAll().deleteAllFromRealm();
                getRealm().commitTransaction();
                return success;
            } catch (Exception e) {
                return false;
            }
        });
    }


    @Override
    public Flowable<Boolean> addSearchItems(ArrayList<SearchItem> items) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                getRealm().copyToRealmOrUpdate(items);
                getRealm().commitTransaction();
                return true;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> addSearchItem(SearchItem item) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                getRealm().copyToRealmOrUpdate(item);
                getRealm().commitTransaction();
                return true;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                return false;
            }
        });
    }
}
