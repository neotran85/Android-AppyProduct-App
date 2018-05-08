package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list;

import android.databinding.ObservableField;
import android.text.TextUtils;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.model.api.product.VerifyOrderRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.common.viewmodel.FetchUserInfoViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class ProductCartListViewModel extends BaseViewModel<ProductCartListNavigator> {

    public ObservableField<Boolean> isCartEmpty = new ObservableField<>(true);
    public ObservableField<Boolean> isCheckedAll = new ObservableField<>(false);
    public ObservableField<Float> totalCost = new ObservableField<>(0.0f);

    public ProductCartListViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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

    private int addressId;

    private void getAllCheckedProductCarts() {
        getCompositeDisposable().add(getDataManager().getAllCheckedProductCarts(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(items -> {
                    // GET SUCCEEDED
                    if (items != null && items.size() > 0) {
                        ArrayList<String> ids = new ArrayList<>();
                        for (ProductCart item : items) {
                            ids.add(item.card_id + "");
                        }
                        getCompositeDisposable().add(getDataManager()
                                .verifyProductOrder(new VerifyOrderRequest(TextUtils.join(",", ids), addressId))
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(result -> {
                                    if (result != null && result.code.equals(ApiCode.BAD_REQUEST_400)) {
                                        LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) result.message;
                                        for(String key: linkedTreeMap.keySet()) {
                                            if(DataUtils.isNumeric(key)) {
                                                if(linkedTreeMap.get(key) instanceof  LinkedTreeMap) {
                                                    LinkedTreeMap<String, Object> singleItems = (LinkedTreeMap<String, Object>) linkedTreeMap.get(key);
                                                }
                                            }
                                        }
                                    }
                                }, Crashlytics::logException));
                    }
                }, Crashlytics::logException));
    }

    public void verifyOrder() {
        getCompositeDisposable().add(getDataManager().getDefaultShippingAddress(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(addressResult -> {
                    // GET SUCCEEDED
                    addressId = addressResult.id;
                    getAllCheckedProductCarts();
                }, Crashlytics::logException));
    }


}
