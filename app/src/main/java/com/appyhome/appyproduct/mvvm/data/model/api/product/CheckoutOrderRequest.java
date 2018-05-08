package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CheckoutOrderRequest {
    @Expose
    @SerializedName("card_id")
    public int card_id;

    @Expose
    @SerializedName("address_id")
    public int address_id;

    @Expose
    @SerializedName("shipping")
    public float shipping;

    @Expose
    @SerializedName("price")
    public float price;

    @Expose
    @SerializedName("promocode_used")
    public String promocode_used;

    @Expose
    @SerializedName("status")
    public String status;
}
