package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CheckoutOrderRequest {
    @Expose
    @SerializedName("cart_id")
    public String cart_id;

    @Expose
    @SerializedName("address_id")
    public int address_id;

    @Expose
    @SerializedName("shipping")
    public double shipping;

    @Expose
    @SerializedName("price")
    public double price;

    @Expose
    @SerializedName("promocode_used")
    public String promocode_used;

    @Expose
    @SerializedName("status")
    public String status;
}
