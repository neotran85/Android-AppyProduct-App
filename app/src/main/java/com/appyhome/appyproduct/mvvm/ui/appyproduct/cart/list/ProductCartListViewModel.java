package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.VerifyOrderViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;

public class ProductCartListViewModel extends BaseViewModel<ProductCartListNavigator> {

    public ObservableField<Boolean> isCartEmpty = new ObservableField<>(true);
    public ObservableField<Boolean> isCheckedAll = new ObservableField<>(false);
    public ObservableField<Float> totalCost = new ObservableField<>(0.0f);
    private VerifyOrderViewModel mVerifyOrderViewModel;
    private FetchUserInfoViewModel mFetchUserInfoViewModel;

    public ProductCartListViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mVerifyOrderViewModel = new VerifyOrderViewModel(dataManager, schedulerProvider);
        mFetchUserInfoViewModel = new FetchUserInfoViewModel(dataManager, schedulerProvider);
    }

    public void setNavigator(ProductCartListNavigator navigator) {
        super.setNavigator(navigator);
        mVerifyOrderViewModel.setNavigator(navigator);
        mFetchUserInfoViewModel.setNavigator(navigator);
    }

    public void fetchShippingAddresses() {
        FetchUserInfoViewModel viewModel = new FetchUserInfoViewModel(getDataManager(), getSchedulerProvider());
        viewModel.fetchShippingAddresses();
    }

    public void emptyProductCarts() {
        getNavigator().showLoading();
        getCompositeDisposable().add(getDataManager().emptyProductCarts(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(success -> {
                    // EMPTY SUCCEEDED
                    emptyProductCartsServer();
                }, Crashlytics::logException));
    }

    private void emptyProductCartsServer() {
        // EMPTY PRODUCT CARTS SERVER
        getCompositeDisposable().add(getDataManager().emptyUserCarts()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(data -> {
                    // SUCCESSFUL TO EMPTY CART
                    getNavigator().closeLoading();
                }, Crashlytics::logException));
    }

    public void getAllProductCarts() {
        getCompositeDisposable().add(getDataManager().getAllProductCarts(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(productCarts -> {
                    isCartEmpty.set(productCarts == null || productCarts.size() <= 0);
                    getNavigator().showCarts(productCarts);
                }, Crashlytics::logException));
    }

    public void doVerifyOrder() {
        mVerifyOrderViewModel.doVerifyOrder();
    }

    public void updateUserInformationAfterFailed() {
        mFetchUserInfoViewModel.fetchUserData();
    }
}
