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
    private Disposable mDisposable = null;

    public ProductCartListViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void emptyProductCarts() {
        getCompositeDisposable().add(getDataManager().emptyProductCarts(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // EMPTY SUCCEEDED
                }, Crashlytics::logException));
    }

    private void disposeGetAllProductCarts() {
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    public void getAllProductCarts() {
        mDisposable = getDataManager().getAllProductCarts(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCarts -> {
                    isCartEmpty.set(productCarts == null || productCarts.size() <= 0);
                    getNavigator().showCarts(productCarts);
                    // Clear disposableGetAllProductCarts
                    disposeGetAllProductCarts();
                }, throwable -> {
                    disposeGetAllProductCarts();
                    throwable.printStackTrace();
                    Crashlytics.logException(throwable);
                });
    }
}
