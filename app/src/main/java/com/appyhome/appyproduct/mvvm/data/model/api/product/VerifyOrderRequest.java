package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VerifyOrderRequest {
    @Expose
    @SerializedName("cart_id")
    public String cart_id;

    @Expose
    @SerializedName("address_id")
    public int address_id;

    public VerifyOrderRequest(String cartIds, int addressId) {
        cart_id = cartIds;
        address_id = addressId;
    }
}
