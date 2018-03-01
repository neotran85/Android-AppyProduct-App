package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ProductCartListViewModel extends BaseViewModel<ProductCartListNavigator> {

    public ProductCartListViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getAllProductCarts(String userId) {
        getCompositeDisposable().add(getDataManager().getAllProductCarts(userId)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCarts -> {
                    getNavigator().showCart(productCarts);
                }, throwable -> {
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                }));
    }
}
