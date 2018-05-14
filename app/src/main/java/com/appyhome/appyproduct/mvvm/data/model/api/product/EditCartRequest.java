package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EditCartRequest {
    @Expose
    @SerializedName("product_id")
    public long product_id;

    @Expose
    @SerializedName("variant_id")
    public long variant_id;

    @Expose
    @SerializedName("new_quantity")
    public int new_quantity;

    public EditCartRequest(long idP, long idV, int amount) {
        new_quantity = amount;
        product_id = idP;
        variant_id = idV;
    }
}
