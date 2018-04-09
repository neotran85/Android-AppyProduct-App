package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EditCartVariantRequest {
    @Expose
    @SerializedName("product_id")
    public int product_id;

    @Expose
    @SerializedName("variant_id")
    public int variant_id;

    @Expose
    @SerializedName("new_variant_id")
    public int new_variant_id;

    public EditCartVariantRequest(int idP, int idV, int newVariantId) {
        new_variant_id = newVariantId;
        product_id = idP;
        variant_id = idV;
    }
}
