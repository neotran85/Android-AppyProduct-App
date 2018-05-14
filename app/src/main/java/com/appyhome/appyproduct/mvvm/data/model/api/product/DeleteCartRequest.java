package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DeleteCartRequest {
    @Expose
    @SerializedName("product_id")
    public long product_id;

    @Expose
    @SerializedName("variant_id")
    public long variant_id;

    public DeleteCartRequest(long idP, long idV) {
        product_id = idP;
        variant_id = idV;
    }
}
