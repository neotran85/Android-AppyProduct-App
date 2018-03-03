package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import io.reactivex.disposables.Disposable;

public class ProductCartListViewModel extends BaseViewModel<ProductCartListNavigator> {

    public ObservableField<Boolean> isCartEmpty = new ObservableField<>(true);
    public ObservableField<Boolean> isCheckedAll = new ObservableField<>(false);
    public ObservableField<String> totalCost = new ObservableField<>("");

    public ProductCartListViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private Disposable disposableGetAllProductCarts = null;

    public void emptyProductCarts() {
        getCompositeDisposable().add(getDataManager().emptyProductCarts()
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // EMPTY SUCCEEDED
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
    public void getAllProductCarts(String userId) {
        disposableGetAllProductCarts = getDataManager().getAllProductCarts(userId)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCarts -> {
                    isCartEmpty.set(productCarts == null || productCarts.size() <= 0);
                    getNavigator().showCart(productCarts);
                    // Clear disposableGetAllProductCarts
                    if (disposableGetAllProductCarts != null) {
                        disposableGetAllProductCarts.dispose();
                        getCompositeDisposable().remove(disposableGetAllProductCarts);
                        disposableGetAllProductCarts = null;
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                });
        getCompositeDisposable().add(disposableGetAllProductCarts);
    }
}
