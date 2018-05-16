package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.common.ShippingType;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

import org.json.JSONObject;

import java.util.HashMap;

public class CartItemViewModel extends ProductCartItemViewModel {
    public ObservableField<String> shippingTypeOpt = new ObservableField<>("");

    private int sellerId;
    private int addressId;
    private String cartIds;

    private String shippingType = ShippingType.LAND;
    private double shippingCost = 0.0;
    public HashMap<String, Double> shippingFees;

    public CartItemViewModel(BaseViewModel baseViewModel) {
        super(baseViewModel.getDataManager(), baseViewModel.getSchedulerProvider());
    }

    public String getShippingType() {
        return shippingType;
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

    public String getShippingCostByMethod(String type) {
        for (String item : shippingFees.keySet()) {
            if (item.equals(type)) {
                String label = type + " (RM " + shippingFees.get(type) + ")";
                return label;
            }
        }
        return "";
    }

    public void updateShippingCost(String type) {
        shippingType = type;
        shippingCost = shippingFees.get(type);
        shippingTypeOpt.set(getShippingCostByMethod(type));
    }

    public void setShippingFees(ProductCart cart) {
        if (cart.shipping_fee != null && cart.shipping_fee.length() > 0) {
            try {
                shippingFees = new HashMap<>();
                JSONObject json = new JSONObject(cart.shipping_fee);
                if (json.has(ShippingType.AIR)) {
                    shippingFees.put(ShippingType.AIR, json.getDouble(ShippingType.AIR));
                    shippingType = ShippingType.AIR;
                }
                if (json.has(ShippingType.SEA)) {
                    shippingFees.put(ShippingType.SEA, json.getDouble(ShippingType.SEA));
                    shippingType = ShippingType.SEA;
                }
                if (json.has(ShippingType.LAND)) {
                    shippingFees.put(ShippingType.LAND, json.getDouble(ShippingType.LAND));
                    shippingType = ShippingType.LAND;
                }
                updateShippingCost(shippingType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public int getSellerId() {
        return sellerId;
    }
}
