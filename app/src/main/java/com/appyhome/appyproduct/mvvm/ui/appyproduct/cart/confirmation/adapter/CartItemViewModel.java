package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.model.api.product.VerifyOrderRequest;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.crashlytics.android.Crashlytics;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class CartItemViewModel extends ProductCartItemViewModel {

    public ObservableField<Double> totalCostOfStore = new ObservableField<>(0.0);
    public ObservableField<Double> shippingCost = new ObservableField<>(0.0);

    private int sellerId;
    private int addressId;
    private String cartIds;


    private LinkedTreeMap<String, Object> shippingTreeMap;

    public CartItemViewModel(BaseViewModel baseViewModel) {
        super(baseViewModel.getDataManager(), baseViewModel.getSchedulerProvider());
    }


    public void updateShippingCost() {
        if (shippingTreeMap != null) {
            updatePrices();
        } else {
            verifyOrder(sellerId, addressId, cartIds);
        }
    }

    private void updatePrices() {
        Double value = getShippingCost(shippingTreeMap.get(sellerId + ""));
        shippingCost.set(value);
        totalCostOfStore.set(totalCostOfStore.get() + value);
    }

    private void verifyOrder(int sellerId, int idAddress, String cartItemIds) {
        getCompositeDisposable().add(getDataManager()
                .verifyProductOrder(new VerifyOrderRequest(cartItemIds, idAddress))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    if (result != null && result.code.equals(ApiCode.OK_200)) {
                        LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) result.message;
                        shippingTreeMap = (LinkedTreeMap<String, Object>) linkedTreeMap.get("shipping");
                        updatePrices();
                    }
                }, Crashlytics::logException));
    }

    private Double getShippingCost(Object object) {
        if (object instanceof Double) {
            return (Double) object;
        } else if (object instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) object;
            if (arrayList.size() > 0) {
                try {
                    Double total = 0.0;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i) instanceof Double)
                            total = total + (Double) arrayList.get(i);
                    }
                    return total;
                } catch (Exception e) {
                    return 0.0;
                }
            }
        }
        return 0.0;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setCartIds(String cartIds) {
        this.cartIds = cartIds;
    }
}
