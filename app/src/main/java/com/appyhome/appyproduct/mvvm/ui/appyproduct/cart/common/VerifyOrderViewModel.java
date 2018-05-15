package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common;

import android.text.TextUtils;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.data.model.api.product.VerifyOrderRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;
import com.crashlytics.android.Crashlytics;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class VerifyOrderViewModel extends BaseViewModel<VerifyOrderNavigator> {

    private int addressId;

    public VerifyOrderViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private boolean verifyTotalPrice(LinkedTreeMap<String, Object> linkedTreeMap, Double totalPrice) {
        try {
            if (linkedTreeMap.containsKey("total_price_available_items")) {
                if (linkedTreeMap.get("total_price_available_items") instanceof Double) {
                    Double getPrice = (Double) linkedTreeMap.get("total_price_available_items");
                    return Math.abs(totalPrice - getPrice) < 0.1; // JUST NEARLY EQUALLY
                }
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        return true;
    }

    private boolean verifyAllItemsAvailable(LinkedTreeMap<String, Object> linkedTreeMap) {
        try {
            for (String key : linkedTreeMap.keySet()) {
                if (DataUtils.isNumeric(key)) {
                    if (linkedTreeMap.get(key) instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> items1 = (LinkedTreeMap<String, Object>) linkedTreeMap.get(key);
                        for (String key1 : items1.keySet()) {
                            if (items1.get(key1) instanceof LinkedTreeMap) {
                                LinkedTreeMap<String, Object> items2 = (LinkedTreeMap<String, Object>) items1.get(key1);
                                for (String key2 : items2.keySet()) {
                                    if (items2.get(key2) instanceof LinkedTreeMap)
                                        if (!verifyOnlyOneItemAvailable((LinkedTreeMap<String, Object>) items2.get(key2)))
                                            return false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        return true;
    }

    private boolean verifyOnlyOneItemAvailable(LinkedTreeMap<String, Object> data) {
        if (data.containsKey("qty_available")) {
            if (data.get("qty_available") instanceof Boolean) {
                return (Boolean) data.get("qty_available");
            }
        }
        return false;
    }

    private void getAllCheckedProductCarts() {
        getCompositeDisposable().add(getDataManager().getAllCheckedProductCarts(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(items -> {
                    // GET SUCCEEDED
                    if (items != null && items.size() > 0) {
                        ArrayList<String> ids = new ArrayList<>();
                        Double totalPrice = 0.0;
                        for (ProductCart item : items) {
                            totalPrice = totalPrice + item.price * item.amount;
                            ids.add(item.cart_id + "");
                        }
                        verifyOrderStepByStep(ids, totalPrice);
                    }
                }, this::doVerifyOrder_FAILED));
    }

    private void verifyOrderStepByStep(ArrayList<String> ids, Double totalPrice) {
        getCompositeDisposable().add(getDataManager()
                .verifyProductOrder(new VerifyOrderRequest(TextUtils.join(",", ids), addressId))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result != null && result.code.equals(ApiCode.OK_200)) {
                        boolean valueVerifyAllItemsAvailable = verifyAllItemsAvailable((LinkedTreeMap<String, Object>) result.message);
                        boolean valueVerifyTotalPrice = verifyTotalPrice((LinkedTreeMap<String, Object>) result.message, totalPrice);
                        if (valueVerifyAllItemsAvailable && valueVerifyTotalPrice) {
                            // PASSED VERIFICATION
                            getNavigator().verifyOrder_PASSED();
                        } else {
                            // FAILED VERIFICATION
                            doVerifyOrder_FAILED(null);
                        }
                    }
                }, this::doVerifyOrder_FAILED));
    }

    private void doVerifyOrder_FAILED(Throwable throwable) {
        Crashlytics.logException(throwable);
        getNavigator().verifyOrder_FAILED("");
    }

    public void doVerifyOrder() {
        getCompositeDisposable().add(getDataManager().getDefaultShippingAddress(getUserId())
                .take(1)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(addressResult -> {
                    // GET SUCCEEDED
                    addressId = addressResult.id;
                    getAllCheckedProductCarts();
                }, this::doVerifyOrder_FAILED));
    }
}
