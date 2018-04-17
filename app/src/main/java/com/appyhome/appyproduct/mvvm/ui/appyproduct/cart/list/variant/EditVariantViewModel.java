package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;

import android.util.Log;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.model.api.product.AddToCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.DeleteCartRequest;
import com.appyhome.appyproduct.mvvm.data.model.api.product.EditCartRequest;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class EditVariantViewModel extends BaseViewModel<EditVariantNavigator> {

    private ProductCartItemViewModel mProductCartItemViewModel;

    public void setProductCartItemViewModel(ProductCartItemViewModel viewModel) {
        mProductCartItemViewModel = viewModel;
    }

    public EditVariantViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public int getAmount() {
        return Integer.valueOf(mProductCartItemViewModel.amount.get());
    }

    public int getVariantId() {
        return mProductCartItemViewModel.getVariantId();
    }

    public String getVariantModelId() {
        return mProductCartItemViewModel.getVariantModelId();
    }

    public long getProductCartId() {
        return mProductCartItemViewModel.getProductCartId();
    }

    public int getProductId() {
        return mProductCartItemViewModel.getProductId();
    }

    public boolean isChecked() {
        return mProductCartItemViewModel.checked.get();
    }

    public void saveProductCartItem(int oldVariantId) {
        // SAVE VARIANT TO LOCAL DB
        getCompositeDisposable().add(getDataManager().updateProductCartItem(getProductCartId(), isChecked(),
                getAmount(), getVariantModelId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCart -> {
                    getNavigator().saveProductCartItem_Done(productCart);
                    deleteOldOneAndAddedNewOne(oldVariantId, productCart);
                }, Crashlytics::logException));
    }

    private void deleteOldOneAndAddedNewOne(int oldVariantId, ProductCart cart) {
        // THEN SAVE VARIANT TO SERVER
        getCompositeDisposable().add(getDataManager().deleteProductToCart(new DeleteCartRequest(cart.product_id, oldVariantId))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(results -> {
                    if (results != null && results.isValid()) {
                        Log.v("saveProductCartItem", "DELETED THE OLD ONE");
                        // THEN ADD NEW VARIANT TO SERVER
                        addOrUpdateProductVariant(cart.product_id, cart.variant_id, cart.amount);
                    }
                }, Crashlytics::logException));
    }

    private void addOrUpdateProductVariant(int productId, int variantId, int amount) {
        getCompositeDisposable().add(getDataManager().addProductToCart(new AddToCartRequest(productId, variantId, amount))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    if (data != null && data.isValid()) {
                        Log.v("addOrUpdateVariant", "ADDED VARIANT SUCCESSFULLY");
                    } else {
                        getCompositeDisposable().add(getDataManager().editProductToCart(new EditCartRequest(productId,
                                variantId, amount))
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(data1 -> {
                                    if (data1 != null && data1.isValid()) {
                                        Log.v("addOrUpdateVariant", "CANNOT ADDED, THEN UPDATED");
                                    }
                                }, Crashlytics::logException));
                    }
                }, Crashlytics::logException));
    }
}
