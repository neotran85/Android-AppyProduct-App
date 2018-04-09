package com.appyhome.appyproduct.mvvm.ui.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddToCartRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.data.remote.ApiMessage;
import com.appyhome.appyproduct.mvvm.utils.helper.NetworkUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseViewModel<N> extends ViewModel {
    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;
    public ObservableField<Integer> isLoadingVisible = new ObservableField<>(View.GONE);
    public ObservableField<Integer> isContentVisible = new ObservableField<>(View.GONE);
    private N mNavigator;
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel() {
        mDataManager = null;
        mSchedulerProvider = null;
    }

    public BaseViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean value) {
        isLoading.set(value);
        isLoadingVisible.set(value ? View.VISIBLE : View.GONE);
        isContentVisible.set(value ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public boolean isUserLoggedIn() {
        return getDataManager().isUserLoggedIn();
    }

    public String getUserId() {
        if (!isUserLoggedIn())
            return "anonymous";
        return getDataManager().getCurrentUserId();
    }

    public boolean isOnline() {
        Activity activity = AppConstants.getFirstActivity();
        if (activity != null)
            return NetworkUtils.isNetworkConnected(activity);
        return false;
    }

    public void updateServerCarts() {
        // EMPTY PRODUCT CARTS FIRST
        getCompositeDisposable().add(getDataManager().emptyUserCarts()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null) {
                        if (data.code.equals(ApiCode.OK_200)) {
                            // ADD PRODUCT CART TO SERVER
                            getCompositeDisposable().add(getDataManager().getAllProductCarts(getUserId())
                                    .observeOn(getSchedulerProvider().ui())
                                    .subscribe(results -> {
                                        if (results != null) {
                                            for (ProductCart cart : results) {
                                                addProductToCartServer(cart.product_id, cart.variant_id, cart.amount);
                                            }
                                        }
                                    }, Crashlytics::logException));
                        }
                    }
                }, Crashlytics::logException));
    }

    private void addProductToCartServer(int productId, int variantId, int amount) {
        getCompositeDisposable().add(getDataManager().addProductToCart(new AddToCartRequest(productId, variantId, amount))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null) {
                        if (data.code.equals(ApiCode.OK_200)) {
                            // UPDATED SUCCESSFUL
                        }
                    }
                }, Crashlytics::logException));
    }
}
