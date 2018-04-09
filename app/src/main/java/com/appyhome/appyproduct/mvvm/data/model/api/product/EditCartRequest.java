package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EditCartRequest {
    @Expose
    @SerializedName("product_id")
    public int product_id;

    @Expose
    @SerializedName("variant_id")
    public int variant_id;

    @Expose
    @SerializedName("new_quantity")
    public int new_quantity;

    public EditCartRequest(int idP, int idV, int amount) {
        new_quantity = amount;
        product_id = idP;
        variant_id = idV;
    }
}
