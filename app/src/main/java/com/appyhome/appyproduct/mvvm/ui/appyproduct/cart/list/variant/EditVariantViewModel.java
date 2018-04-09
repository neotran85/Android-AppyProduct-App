package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.variant;

import android.util.Log;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.model.api.product.EditCartVariantRequest;
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

    public void saveProductCartItem(int oldVariantId) {
        // SAVE VARIANT TO LOCAL DB
        getCompositeDisposable().add(getDataManager().updateProductCartItem(mProductCartItemViewModel.getProductCartId(), mProductCartItemViewModel.checked.get(),
                Integer.valueOf(mProductCartItemViewModel.amount.get()), mProductCartItemViewModel.getVariantModelId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCart -> {
                    getNavigator().saveProductCartItem_Done(productCart);
                }, Crashlytics::logException));
        // THEN SAVE VARIANT TO SERVER
        getCompositeDisposable().add(getDataManager().editProductCartVariant(new EditCartVariantRequest(mProductCartItemViewModel.getProductId(),
                oldVariantId, mProductCartItemViewModel.getVariantId()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(results -> {
                    if (results != null && results.isValid()) {
                        Log.v("editProductCartVariant", "VARIANT EDITED SUCCESSFULLY");
                    }
                }, Crashlytics::logException));
    }
}
