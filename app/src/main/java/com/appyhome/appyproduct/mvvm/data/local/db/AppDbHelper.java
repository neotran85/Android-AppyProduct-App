package com.appyhome.appyproduct.mvvm.data.local.db;

import android.text.TextUtils;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
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
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Seller;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.User;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductCartResponse;
import com.appyhome.appyproduct.mvvm.data.model.api.product.ProductFavoriteResponse;

import java.util.ArrayList;
import java.util.Random;

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
    private float[] prices = {100.50f, 25.50f, 12.60f, 50.78f, 51.2f, 12.62f};
    private float[] rates = {3, 4.5f, 5, 2, 1, 0};
    private int[] numberRates = {30, 415, 52, 211, 123, 985};
    private int[] numberFavorites = {130, 15, 522, 11, 210, 205};
    private int[] discountList = {0, 16, 0, 42, 12, 0};

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
    public Flowable<ProductCached> getProductCachedById(int idProduct) {
        beginTransaction();
        ProductCached product = getRealm().where(ProductCached.class)
                .equalTo("id", idProduct)
                .findFirst();
        if (product == null)
            product = new ProductCached();
        getRealm().commitTransaction();
        return product.asFlowable();
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
            for (Product product : list) {
                int randomNum = new Random().nextInt(rates.length);
                product.rate = rates[randomNum];
                product.discount = discountList[randomNum];
                product.rate_count = numberRates[randomNum];
                product.favorite_count = numberFavorites[randomNum];
                getRealm().copyToRealmOrUpdate(product);
            }
            getRealm().commitTransaction();
            return Flowable.just(true);
        } catch (Exception e) {
            getRealm().cancelTransaction();
            return Flowable.just(false);
        }
    }

    @Override
    public Flowable<Boolean> addProductsCached(RealmList<ProductCached> list) {
        try {
            beginTransaction();
            for (ProductCached product : list) {
                int randomNum = new Random().nextInt(rates.length);
                product.rate = rates[randomNum];
                product.discount = discountList[randomNum];
                product.rate_count = numberRates[randomNum];
                product.favorite_count = numberFavorites[randomNum];
                product.time_db_added = System.currentTimeMillis();
                getRealm().copyToRealmOrUpdate(product);
            }
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
        beginTransaction();
        Flowable<AppyAddress> address = getRealm().where(AppyAddress.class)
                .equalTo("user_id", userId)
                .equalTo("is_default", 1)
                .findFirst().asFlowable();
        getRealm().commitTransaction();
        return address;
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

    private ProductCart isContaining(RealmResults<ProductCart> results, ProductCartResponse response) {
        for (ProductCart item : results) {
            if (response.product_id == item.product_id
                    && response.variant_id == item.variant_id) {
                return item;
            }
        }
        return null;
    }

    private ProductFavorite isContaining(RealmResults<ProductFavorite> results, ProductFavoriteResponse response) {
        for (ProductFavorite item : results) {
            if (response.product_id == item.product_id
                    && response.variant_id == item.variant_id) {
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
            RealmResults<ProductCart> carts = getRealm().where(ProductCart.class)
                    .equalTo("user_id", userId)
                    .equalTo("order_id", 0)
                    .sort("time_added", Sort.DESCENDING)
                    .findAll();
            long newId = System.currentTimeMillis();
            if (carts != null && carts.isValid() && carts.size() > 0) {
                ArrayList<ProductCart> totalArray = new ArrayList<>();
                for (ProductCartResponse response : array) {
                    newId++;
                    ProductCart cartItem = isContaining(carts, response);
                    if (cartItem == null) {
                        cartItem = new ProductCart();
                        cartItem.id = newId;
                        cartItem.product_image = response.product_image;
                        cartItem.checked = true;
                    }
                    cartItem = inputProductCart(userId, cartItem, response);
                    totalArray.add(cartItem);
                }
                getRealm().copyToRealmOrUpdate(totalArray);
            } else {
                ArrayList<ProductCart> arrayList = new ArrayList<>();
                for (ProductCartResponse response : array) {
                    newId++;
                    ProductCart cartItem = new ProductCart();
                    cartItem.id = newId;
                    cartItem.checked = true;
                    cartItem.product_image = response.product_image;
                    cartItem = inputProductCart(userId, cartItem, response);
                    arrayList.add(cartItem);
                }
                getRealm().copyToRealmOrUpdate(arrayList);
            }
            getRealm().commitTransaction();
            return true;
        });
    }

    @Override
    public Flowable<Boolean> syncAllProductFavorite(String userId, ArrayList<ProductFavoriteResponse> array) {
        return Flowable.fromCallable(() -> {
            beginTransaction();
            RealmResults<ProductFavorite> favorites = getRealm().where(ProductFavorite.class)
                    .equalTo("user_id", userId)
                    .findAll();
            long newId = System.currentTimeMillis();
            ArrayList<ProductFavorite> totalArray;
            if (favorites != null && favorites.isValid() && favorites.size() > 0) {
                totalArray = new ArrayList<>();
                for (ProductFavoriteResponse response : array) {
                    newId++;
                    ProductFavorite favItem = isContaining(favorites, response);
                    if (favItem == null) {
                        favItem = new ProductFavorite();
                        favItem.id = newId;
                        favItem.product_avatar = response.product_avatar;
                    }
                    favItem = inputProductFavorite(userId, favItem, response);
                    totalArray.add(favItem);
                }
            } else {
                totalArray = new ArrayList<>();
                for (ProductFavoriteResponse response : array) {
                    newId++;
                    ProductFavorite favItem = new ProductFavorite();
                    favItem.id = newId;
                    favItem.product_avatar = response.product_avatar;
                    favItem = inputProductFavorite(userId, favItem, response);
                    totalArray.add(favItem);
                }
            }
            getRealm().copyToRealmOrUpdate(totalArray);
            getRealm().commitTransaction();
            return true;
        });
    }

    private ProductFavorite inputProductFavorite(String userId, ProductFavorite favItem, ProductFavoriteResponse item) {
        favItem.variant_price = item.variant_price;
        favItem.product_id = item.product_id;
        favItem.product_name = item.product_name;
        favItem.variant_stock_left = item.variant_stock_left;
        favItem.user_id = userId;
        favItem.variant_model_id = item.product_id + "_" + item.variant_id;
        favItem.variant_id = item.variant_id;
        favItem.variant_name = item.variant_name;
        return favItem;
    }

    private ProductCart inputProductCart(String userId, ProductCart cartItem, ProductCartResponse item) {
        cartItem.price = item.variant_price;
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
    public Flowable<ProductCart> updateProductCartItem(long idProductCart, boolean checked, int amount, String variantModelId) {
        try {
            beginTransaction();
            ProductCart productCart = getRealm().where(ProductCart.class)
                    .equalTo("id", idProductCart)
                    .findFirst();
            productCart.checked = checked;
            productCart.amount = amount;
            if (variantModelId.length() > 0) {
                ProductVariant variant = getRealm().where(ProductVariant.class)
                        .equalTo("model_id", variantModelId).findFirst();
                if (variant != null && variant.isValid()) {
                    productCart.variant_name = variant.variant_name;
                    productCart.variant_id = variant.id;
                    productCart.variant_model_id = variant.model_id;
                    productCart.variant_stock = variant.quantity;
                    productCart.price = variant.price;
                    productCart.product_image = variant.avatar;
                }
            }
            RealmResults<ProductCart> productCarts = getRealm().where(ProductCart.class)
                    .equalTo("user_id", productCart.user_id)
                    .notEqualTo("id", productCart.id)
                    .equalTo("product_id", productCart.product_id)
                    .equalTo("variant_model_id", productCart.variant_model_id)
                    .findAll();
            if (productCarts != null && productCarts.size() > 0) {
                // SUM
                int totalAmount = 0;
                for (ProductCart item : productCarts) {
                    totalAmount = totalAmount + item.amount;
                }
                productCart.amount = productCart.amount + totalAmount;
                productCarts.deleteAllFromRealm();
            }

            productCart = getRealm().copyToRealmOrUpdate(productCart);
            getRealm().commitTransaction();
            return productCart.asFlowable();
        } catch (Exception e) {
            getRealm().cancelTransaction();
            return new ProductCart().asFlowable();
        }
    }

    private ProductCart createNewProductCart(Product product, String userId, ProductVariant variant) {
        ProductCart cartItem = new ProductCart();
        cartItem.id = System.currentTimeMillis();
        cartItem.price = variant.price;
        cartItem.product_id = product.id;
        cartItem.seller_id = product.seller_id;
        cartItem.seller_name = product.seller_name;
        cartItem.product_name = product.product_name;
        cartItem.amount = 0;
        cartItem.checked = true;
        cartItem.product_image = variant.avatar;
        cartItem.user_id = userId;
        cartItem.order_id = 0;
        cartItem.variant_id = variant.id;
        cartItem.variant_stock = variant.quantity;
        cartItem.variant_model_id = variant.model_id;
        cartItem.variant_name = variant.variant_name;
        return cartItem;
    }

    @Override
    public Flowable<ProductCart> getProductCart(String userId, int productId) {
        try {
            beginTransaction();
            ProductCart productCart = getRealm().where(ProductCart.class)
                    .equalTo("product_id", productId)
                    .equalTo("user_id", userId)
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
    public Flowable<ProductCart> addProductToCart(String userId, int productId, String variantModelId, int amountAdded) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                ProductCart productCart = getRealm().where(ProductCart.class)
                        .equalTo("product_id", productId)
                        .equalTo("variant_model_id", variantModelId)
                        .equalTo("user_id", userId)
                        .equalTo("order_id", 0)
                        .findFirst();
                if (productCart == null || !productCart.isValid()) {
                    ProductCached product = getRealm().where(ProductCached.class)
                            .equalTo("id", productId)
                            .findFirst();
                    ProductVariant variant = getRealm().where(ProductVariant.class)
                            .equalTo("model_id", variantModelId)
                            .findFirst();
                    if (variant != null) {
                        productCart = createNewProductCart(product.convertToProduct(), userId, variant);
                    }
                }
                if (productCart != null) {
                    long timeAdded = System.currentTimeMillis();
                    productCart.time_added = timeAdded;
                    timeAdded--;
                    productCart.amount = productCart.amount + amountAdded;
                    productCart = getRealm().copyToRealmOrUpdate(productCart);

                    // Update time for all seller items
                    RealmResults<ProductCart> productCarts = getRealm().where(ProductCart.class)
                            .equalTo("seller_name", productCart.seller_name)
                            .equalTo("user_id", userId)
                            .equalTo("order_id", 0)
                            .sort("time_added", Sort.DESCENDING)
                            .findAll();
                    for (ProductCart cart : productCarts) {
                        cart.time_added = timeAdded;
                        timeAdded--;
                    }
                    getRealm().copyToRealmOrUpdate(productCarts);
                }
                getRealm().commitTransaction();
                return productCart;
            } catch (Exception e) {
                getRealm().cancelTransaction();
                e.printStackTrace();
            }
            return new ProductCart();
        });
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
            order.customer_id = customerId;
            order.payment_method = paymentMethod;
            order.shipping_address = shippingAddress;
            order.cart = new RealmList<>();
            order.total_cost = totalCost;
            for (ProductCart item : items) {
                item.order_id = order.id;
                order.cart.add(item);
            }
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
    public Flowable<Boolean> addOrRemoveFavorite(int productId, int variantId, String userId) {
        return Flowable.fromCallable(() -> {
            try {
                Boolean value = false;
                beginTransaction();

                ProductCached product = getRealm().where(ProductCached.class)
                        .equalTo("id", productId)
                        .findFirst();

                ProductVariant variant = getRealm().where(ProductVariant.class)
                        .equalTo("id", variantId)
                        .equalTo("product_id", productId)
                        .findFirst();

                ProductFavorite favorite = getRealm().where(ProductFavorite.class)
                        .equalTo("user_id", userId)
                        .equalTo("product_id", productId)
                        .equalTo("variant_id", variantId).findFirst();

                if (favorite == null || !favorite.isValid()) {
                    favorite = new ProductFavorite(userId, product, variant);
                    getRealm().copyToRealmOrUpdate(favorite);
                    value = true;
                } else {
                    favorite.deleteFromRealm();
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

    private void beginTransaction() {
        if (getRealm().isInTransaction()) {
            getRealm().commitTransaction();
        }
        getRealm().beginTransaction();
    }

    @Override
    public Flowable<Boolean> isProductFavorite(String userId, int productId, int variantId) {
        return Flowable.fromCallable(() -> {
            try {
                beginTransaction();
                RealmQuery<ProductFavorite> query = getRealm().where(ProductFavorite.class)
                        .equalTo("user_id", userId)
                        .equalTo("product_id", productId);
                if (variantId > -1) {
                    query = query.equalTo("variant_id", variantId);
                }
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
        if (filter != null) {
            if (filter.shipping_from != null && filter.shipping_from.length() > 0) {
                if (filter.shipping_from.equals("Local"))
                    query = query.equalTo("stock_location", "MY");
                else
                    query = query.notEqualTo("stock_location", "MY");
            }
            float min = filter.price_min > 0 ? filter.price_min : 0;
            float max = filter.price_max > 0 ? filter.price_max : 1000000000;
            query = query.between("lowest_price", min, max);
            if (filter.rating >= 0)
                query = query.greaterThanOrEqualTo("rate", filter.rating);
        }
        Flowable<RealmResults<Product>> result = query.findAll().asFlowable();
        getRealm().commitTransaction();
        return result;
    }

    @Override
    public Flowable<RealmResults<ProductFavorite>> getAllProductFavorites(String userId) {
        beginTransaction();
        Flowable<RealmResults<ProductFavorite>> favorites = getRealm().where(ProductFavorite.class)
                .equalTo("user_id", userId)
                .sort("id", Sort.DESCENDING)
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
