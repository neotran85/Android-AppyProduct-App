package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DeleteWishListRequest {
    @Expose
    @SerializedName("product_id")
    public long product_id;

    @Expose
    @SerializedName("variant_id")
    public int variant_id;

    public DeleteWishListRequest(long idP, int idV) {
        product_id = idP;
        variant_id = idV;
    }
}
