package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

import io.reactivex.disposables.Disposable;

public class ProductCartListViewModel extends BaseViewModel<ProductCartListNavigator> {

    public ProductCartListViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    private Disposable disposableGetAllProductCarts = null;
    public void getAllProductCarts(String userId) {
        disposableGetAllProductCarts = getDataManager().getAllProductCarts(userId)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCarts -> {
                    getNavigator().showCart(productCarts);
                    if (disposableGetAllProductCarts != null)
                        disposableGetAllProductCarts.dispose();
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                });
        getCompositeDisposable().add(disposableGetAllProductCarts);
    }
}
