package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.databinding.ObservableField;
import android.util.Log;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddToCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddWishListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.DeleteWishListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.EditCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.GetShippingRequest;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.AppyProductConstants;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import io.realm.RealmResults;

public class ProductItemViewModel extends BaseViewModel<ProductItemNavigator> {
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> variantName = new ObservableField<>("");
    public ObservableField<String> warranty = new ObservableField<>("");
    public ObservableField<String> imageURL = new ObservableField<>("");
    public ObservableField<String> price = new ObservableField<>("0");
    public ObservableField<Float> rate = new ObservableField<>(0f);
    public ObservableField<String> rateCount = new ObservableField<>("");
    public ObservableField<String> favoriteCount = new ObservableField<>("");
    public ObservableField<String> discount = new ObservableField<>("");
    public ObservableField<Boolean> isProductFavorite = new ObservableField<>(false);
    public ObservableField<Boolean> isVariantFavorite = new ObservableField<>(false);
    public ObservableField<Boolean> isEditVariantShowed = new ObservableField<>(false);
    public ObservableField<Boolean> isSmall = new ObservableField<>(false);
    public ObservableField<Float> smallestSize = new ObservableField<>(0.0f);
    public ObservableField<Boolean> isDiscount = new ObservableField<>(false);
    public ObservableField<Boolean> isVariantSelected = new ObservableField<>(false);
    public ObservableField<String> stockCount = new ObservableField<>("0");
    public ObservableField<String> sellerName = new ObservableField<>("");
    public ObservableField<String> promotionBannerURL = new ObservableField<>(AppyProductConstants.RESOURCE_URL.PRODUCT_DETAIL_PROMOTION_URL);
    public ObservableField<Float> alphaTitle = new ObservableField<>(0.0f);
    public ObservableField<String> shippingFee = new ObservableField<>("");
    public ObservableField<String> shippingLocation = new ObservableField<>("");

    private int productId;

    private int variantId = -1;

    private RealmResults<ProductVariant> mVariants;

    public ProductItemViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        smallestSize.set(AppyProductConstants.LAYOUT_SIZE.PRODUCT_SMALLEST_SIZE);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int id) {
        productId = id;
    }

    public void updateProductFavorite(int position) {
        getCompositeDisposable().add(getDataManager().addOrRemoveFavorite(getProductId(), getVariantId(), getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(value -> {
                    updateUserWishList(getProductId(), getVariantId(), value);
                    isVariantFavorite.set(value);
                    int count = Integer.valueOf(favoriteCount.get());
                    count = value ? count + 1 : count - 1;
                    favoriteCount.set(count + "");
                    getNavigator().notifyFavoriteChanged(position, value);
                }, Crashlytics::logException));
    }

    public void addProductToCart(String variantModelId, int amount, boolean isBuyNow) {
        getCompositeDisposable().add(getDataManager().addProductToCart(getUserId(), productId, variantModelId, amount)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCart -> {
                    if (productCart != null && getUserId().equals(productCart.user_id)) {
                        getNavigator().updateCartCount();
                        getNavigator().addedToCartCompleted(amount, isBuyNow);
                        addProductCartServer(productCart);
                    }
                }, Crashlytics::logException));
    }

    public void inputValue(Product product, boolean isAllFavorited) {
        inputValue(product);
        isProductFavorite.set(isAllFavorited);
    }

    private void inputValue(Product product) {
        title.set(product.product_name);
        sellerName.set(product.seller_name);
        imageURL.set(product.avatar_name);
        productId = product.id;
        price.set("RM " + product.lowest_price);
        rate.set(product.rate);
        rateCount.set(product.rate_count + "");
        discount.set(product.discount + "%");
        isDiscount.set(product.discount > 0);
        favoriteCount.set(product.favorite_count + "");
    }

    public void updateIfProductFavorite() {
        checkIfFavorite(getUserId(), getProductId(), -1);
    }

    public void updateIfVariantFavorite() {
        checkIfFavorite(getUserId(), getProductId(), getVariantId());
    }

    private void checkIfFavorite(String userId, int productId, int variantId) {
        getCompositeDisposable().add(getDataManager().isProductFavorite(userId, productId, variantId)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(isLiked -> {
                    if (variantId == -1) isProductFavorite.set(isLiked);
                    else isVariantFavorite.set(isLiked);
                }, Crashlytics::logException));
    }

    public void getProductCachedById() {
        getCompositeDisposable().add(getDataManager().getProductCachedById(getProductId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCached -> {
                    if (productCached != null && productCached.isValid()) {
                        inputValue(productCached.convertToProduct());
                        checkIfFavorite(getUserId(), getProductId(), getVariantId());
                    }
                }, Crashlytics::logException));
    }

    private void editProductCartServer(ProductCart cart) {
        getCompositeDisposable().add(getDataManager().editProductToCart(new EditCartRequest(cart.product_id, cart.variant_id, cart.amount))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        Log.v("editProductCartServer", "UPDATED SUCCESSFUL");
                    }
                }, Crashlytics::logException));
    }

    private void addProductCartServer(ProductCart cart) {
        getCompositeDisposable().add(getDataManager().addProductToCart(new AddToCartRequest(cart.product_id, cart.variant_id, cart.amount))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        Log.v("addProductCartServer", "UPDATED SUCCESSFUL");
                    } else {
                        editProductCartServer(cart);
                    }
                }, Crashlytics::logException));
    }

    private void updateUserWishList(int pProductId, int pVariantId, boolean isFavorite) {
        getCompositeDisposable().add((isFavorite ? getDataManager().addUserWishList(new AddWishListRequest(pProductId, pVariantId))
                : getDataManager().deleteUserWishList(new DeleteWishListRequest(pProductId, pVariantId)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        Log.v("addUserWishList", "ADDED WISH LIST SUCCESSFUL");
                    }
                }, Crashlytics::logException));
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int id) {
        this.variantId = id;
    }


    /***************** SHIPPING FREE *****************/


    public void getDefaultShippingAddress(ProductVariant variant) {
        if (isUserLoggedIn())
            getCompositeDisposable().add(getDataManager().getDefaultShippingAddress(getUserId())
                    .take(1)
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(addressResult -> {
                        getShippingFee(addressResult, variant);
                    }, Crashlytics::logException));
        else {
            getShippingFee(null, variant);
        }
    }

    private void getShippingFee(Address addressResult, ProductVariant variant) {
        String customMode = variant.isLocal() ? "AIR" : "SEA";
        String locationName = "Kuala Lumpur";
        String postCode = "55904";
        shippingFee.set("");
        if (addressResult != null) {
            locationName = addressResult.avatar;
            if (addressResult.post_code.length() > 0) {
                postCode = addressResult.post_code;
            } else {
                shippingLocation.set(locationName);
                shippingFee.set("Unknown");
                return;
            }
        }
        shippingLocation.set(locationName);
        getCompositeDisposable().add(getDataManager().getShippingFee(new GetShippingRequest(variant.product_id, customMode, postCode, variant.weight))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        shippingFee.set(getString(R.string.rm) + " " + data.price);
                    } else shippingFee.set(getString(R.string.fee_unknown));
                }, Crashlytics::logException));
    }
}
