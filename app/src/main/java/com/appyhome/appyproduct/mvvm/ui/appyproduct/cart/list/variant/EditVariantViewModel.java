package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;

import android.databinding.ObservableField;
import android.util.Log;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddToCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.EditCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.EditCartVariantRequest;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class EditVariantViewModel extends BaseViewModel<EditVariantNavigator> implements FetchUserInfoNavigator {

    public ObservableField<String> confirmButtonText = new ObservableField<>("CONFIRM");
    private ProductCartItemViewModel mProductCartItemViewModel;
    private ProductVariant mSelectedVariant;

    private FetchUserInfoViewModel mFetchUserInfoViewModel;

    private long mOldVariantId = 0;


    public EditVariantViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mFetchUserInfoViewModel = new FetchUserInfoViewModel(dataManager, schedulerProvider);
        mFetchUserInfoViewModel.setNavigator(this);
    }

    public void setProductCartItemViewModel(ProductCartItemViewModel viewModel) {
        mProductCartItemViewModel = viewModel;
    }

    public int getAmount() {
        return Integer.valueOf(mProductCartItemViewModel.amount.get());
    }

    public long getVariantId() {
        return mProductCartItemViewModel.getVariantId();
    }

    public long getProductId() {
        return mProductCartItemViewModel.getProductId();
    }

    public boolean isChecked() {
        return mProductCartItemViewModel.checked.get();
    }

    public void saveProductCartItem(long oldVariantId) {
        confirmButtonText.set("Verifying...");
        if (oldVariantId < 0) {
            addNewCart();
        } else {
            getCompositeDisposable().add(getDataManager().editProductCartVariant(new EditCartVariantRequest(getProductId(), oldVariantId, getVariantId()))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(data -> {
                        if (data != null && data.isValid()) {
                            if (data.isVariantQuantityUpdated()) {
                                if (mFetchUserInfoViewModel != null)
                                    mFetchUserInfoViewModel.fetchAndSyncCartsServer();
                            } else editProductCartQuantity();
                        } else {
                            addNewCart();
                        }
                    }, Crashlytics::logException));
        }
    }

    private void editProductCartQuantity() {
        getCompositeDisposable().add(getDataManager().editProductToCart(new EditCartRequest(getProductId(), getVariantId(), getAmount()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        Log.v("editProductCartServer", "CART EDITED SUCCESSFUL");
                    }
                    if (mFetchUserInfoViewModel != null)
                        mFetchUserInfoViewModel.fetchAndSyncCartsServer();
                }, Crashlytics::logException));
    }

    private void addNewCart() {
        getCompositeDisposable().add(getDataManager().addProductToCart(new AddToCartRequest(getProductId(), getVariantId(), getAmount()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        Log.v("addOrUpdateVariant", "ADDED VARIANT SUCCESSFULLY");
                    }
                    if (mFetchUserInfoViewModel != null)
                        mFetchUserInfoViewModel.fetchAndSyncCartsServer();
                }, Crashlytics::logException));
    }

    public ProductVariant getSelectedVariant() {
        return mSelectedVariant;
    }

    public void setSelectedVariant(ProductVariant selectedVariant) {
        this.mSelectedVariant = selectedVariant;
    }

    public void confirmChanges() {
        saveProductCartItem(mOldVariantId);
    }

    @Override
    public void onFetchUserInfo_Done() {
        confirmButtonText.set("CONFIRM");
        getNavigator().saveProductCartItem_Done();
    }

    @Override
    public void onFetchUserInfo_Failed() {
        // DO NOTHING
    }

    public void setOldVariantId(long oldVariantId) {
        this.mOldVariantId = oldVariantId;
    }
}
