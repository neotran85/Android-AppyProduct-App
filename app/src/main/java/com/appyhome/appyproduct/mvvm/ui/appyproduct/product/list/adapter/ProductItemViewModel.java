package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.databinding.ObservableField;
import android.util.Log;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Seller;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddWishListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.DeleteWishListRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.GetShippingRequest;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.AppyProductConstants;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.ProductDetailActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ProductItemViewModel extends BaseViewModel<ProductItemNavigator> implements FetchUserInfoNavigator {
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> variantName = new ObservableField<>("");
    public ObservableField<String> imageURL = new ObservableField<>("");
    public ObservableField<Double> lowestPrice = new ObservableField<>(0.0);
    public ObservableField<Double> price = new ObservableField<>(0.0);
    public ObservableField<Float> rate = new ObservableField<>(0f);
    public ObservableField<String> rateCount = new ObservableField<>("");
    public ObservableField<String> favoriteCount = new ObservableField<>("");
    public ObservableField<String> discount = new ObservableField<>("");
    public ObservableField<Boolean> isProductFavorite = new ObservableField<>(false);
    public ObservableField<Boolean> isEditVariantShowed = new ObservableField<>(false);
    public ObservableField<Boolean> isDiscount = new ObservableField<>(false);
    public ObservableField<Boolean> isVariantSelected = new ObservableField<>(false);
    public ObservableField<String> stockCount = new ObservableField<>("0");
    public ObservableField<String> sellerName = new ObservableField<>("");
    public ObservableField<String> promotionBannerURL = new ObservableField<>(AppyProductConstants.RESOURCE_URL.PRODUCT_DETAIL_PROMOTION_URL);
    public ObservableField<Float> alphaTitle = new ObservableField<>(0.0f);
    public ObservableField<String> shippingFee = new ObservableField<>("");
    public ObservableField<String> shippingLocation = new ObservableField<>("");
    public ObservableField<String> sellerAvatar = new ObservableField<>("");
    public ObservableField<SampleAdapter> relatedAdapter = new ObservableField<>(new ProductAdapter());
    public ObservableField<SampleAdapter> productsAdapter = new ObservableField<>(new ProductAdapter());
    public ObservableField<Boolean> isRelatedProductsShowed = new ObservableField<>(true);

    protected long productId;

    private int variantId = -1;

    private int mDetailType = 0;

    public ProductItemViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long id) {
        productId = id;
    }

    public void updateProductFavorite(int position) {
        getCompositeDisposable().add(getDataManager().addOrRemoveFavorite(getProductId(), getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(value -> {
                    updateUserWishList(getProductId(), getVariantId(), value);
                    isProductFavorite.set(value);
                    int count = Integer.valueOf(favoriteCount.get());
                    count = value ? count + 1 : count - 1;
                    count = count > 0 ? count : 0;
                    favoriteCount.set(count + "");
                    getNavigator().notifyFavoriteChanged(position, value);
                }, Crashlytics::logException));
    }

    public void inputValue(Product product) {
        isProductFavorite.set(product.wishlist != null && product.wishlist.equals(getUserId()));
        title.set(product.product_name);
        sellerName.set(product.seller_name);
        imageURL.set(product.avatar_name);
        productId = product.id;
        lowestPrice.set(product.lowest_price);
        rate.set(product.rate);
        rateCount.set(product.rate_count + "");
        discount.set(product.discount + "%");
        isDiscount.set(product.discount > 0);
        favoriteCount.set(product.favorite_count + "");
    }

    public void updateIfProductFavorite() {
        checkIfFavorite(getUserId(), getProductId());
    }

    public void updateIfVariantFavorite() {
        checkIfFavorite(getUserId(), getProductId());
    }

    private void checkIfFavorite(String userId, long productId) {
        getCompositeDisposable().add(getDataManager().isProductFavorite(userId, productId)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(isLiked -> {
                    isProductFavorite.set(isLiked);
                }, Crashlytics::logException));
    }

    public void getProductCachedById() {
        getCompositeDisposable().add(getDataManager().getProductCachedById(getProductId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCached -> {
                    if (productCached != null && productCached.isValid()) {
                        inputValue(productCached);
                        checkIfFavorite(getUserId(), getProductId());
                    }
                }, Crashlytics::logException));
    }

    private void updateUserWishList(long pProductId, int pVariantId, boolean isFavorite) {
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

    public void calculateShippingFee(ProductVariant variant) {
        String postCode = getDataManager().getDefaultShippingPostCode();
        String location = getDataManager().getDefaultShippingLocation();
        calculateShippingFee(location, postCode, variant);
    }

    public void calculateShippingFee(String area, String post_code, ProductVariant variant) {
        String customMode = variant.isLocal() ? "AIR" : "SEA";
        String locationName = "Kuala Lumpur";
        String postCode = "55904";

        shippingFee.set("");

        if (area != null && area.length() > 0) {
            locationName = area;
            postCode = post_code;
        }

        shippingLocation.set("Shipping to: " + locationName);
        getDataManager().setDefaultShippingLocation(locationName);
        getDataManager().setDefaultShippingPostCode(postCode);

        if (post_code.length() <= 0) {
            shippingFee.set("Unknown");
        } else
            getCompositeDisposable().add(getDataManager().fetchShippingFee(new GetShippingRequest(variant.product_id, customMode, postCode, variant.weight))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(data -> {
                        if (data != null && data.isValid()) {
                            shippingFee.set(getString(R.string.rm) + " " + data.price);
                        } else shippingFee.set(getString(R.string.fee_unknown));
                    }, Crashlytics::logException));
    }

    public void fetchSellerInformation(long sellerId) {
        getCompositeDisposable().add(getDataManager().fetchSellerInformation(sellerId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        sellerAvatar.set(data.seller.avatar);
                        addSeller(data.seller);
                    }
                }, Crashlytics::logException));
    }

    private void addSeller(Seller seller) {
        getCompositeDisposable().add(getDataManager().addSeller(seller)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // UPDATE SELLER INFORMATION
                }, Crashlytics::logException));
    }

    // RELATED PRODUCTS
    public void getRelatedProducts(int type) {
        mDetailType = type;
        FetchUserInfoViewModel fetchUserInfoViewModel;
        switch (mDetailType) {
            case ProductDetailActivity.DETAIL_PRODUCT:
                break;
            case ProductDetailActivity.DETAIL_FAVORITE:
                fetchUserInfoViewModel = new FetchUserInfoViewModel(getDataManager(), getSchedulerProvider());
                fetchUserInfoViewModel.setNavigator(this);
                fetchUserInfoViewModel.fetchAndSyncWishListServer();
                break;
            case ProductDetailActivity.DETAIL_CART:
                fetchUserInfoViewModel = new FetchUserInfoViewModel(getDataManager(), getSchedulerProvider());
                fetchUserInfoViewModel.setNavigator(this);
                fetchUserInfoViewModel.fetchAndSyncCartsServer();
                break;
        }
    }

    private void getRelatedProductsFromCart() {
        getCompositeDisposable().add(getDataManager().getCartPadding(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        getNavigator().showRelatedProducts(data);
                    }
                }, Crashlytics::logException));
    }

    private void getRelatedProductsFromWishList() {
        getCompositeDisposable().add(getDataManager().getWishListPadding(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        getNavigator().showRelatedProducts(data);
                    }
                }, Crashlytics::logException));
    }

    @Override
    public void onFetchUserInfo_Done() {
        switch (mDetailType) {
            case ProductDetailActivity.DETAIL_PRODUCT:
                break;
            case ProductDetailActivity.DETAIL_FAVORITE:
                getRelatedProductsFromWishList();
                break;
            case ProductDetailActivity.DETAIL_CART:
                getRelatedProductsFromCart();
                break;
        }
    }

    @Override
    public void onFetchUserInfo_Failed() {
        onFetchUserInfo_Done();
    }
}
