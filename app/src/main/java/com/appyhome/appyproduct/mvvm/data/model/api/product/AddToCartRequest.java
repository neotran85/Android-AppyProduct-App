package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddToCartRequest {
    @Expose
    @SerializedName("product_id")
    public int product_id;

    @Expose
    @SerializedName("variant_id")
    public int variant_id;

    @Expose
    @SerializedName("quantity")
    public int quantity;

    public AddToCartRequest(int idP, int idV, int amount) {
        quantity = amount;
        product_id = idP;
        variant_id = idV;
    }
}
