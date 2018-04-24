package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.variant;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import io.realm.RealmList;

public class ProductVariantViewModel extends BaseViewModel<ProductVariantNavigator> {

    public ObservableField<String> selectedVariantName = new ObservableField<>("");
    private int mProductId;

    public ProductVariantViewModel(DataManager dataManager,
                                   SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private void addProductVariants(RealmList<ProductVariant> productVariants) {
        getCompositeDisposable().add(getDataManager().addProductVariants(productVariants)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    getProductVariants();
                }, Crashlytics::logException));
    }

    private void getProductVariants() {
        getCompositeDisposable().add(getDataManager().getProductVariants(mProductId)
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(results -> {
                    getNavigator().showProductVariants(results);
                }, Crashlytics::logException));
    }

    public void fetchProductVariant(int idProduct) {
        mProductId = idProduct;
        getCompositeDisposable().add(getDataManager()
                .fetchProductVariant(mProductId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result != null && result.code.equals(ApiCode.OK_200))
                        addProductVariants(result.message);
                }, Crashlytics::logException));
    }
}
