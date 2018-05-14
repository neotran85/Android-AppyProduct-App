package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EditCartVariantRequest {
    @Expose
    @SerializedName("product_id")
    public long product_id;

    @Expose
    @SerializedName("variant_id")
    public long variant_id;

    @Expose
    @SerializedName("new_variant_id")
    public long new_variant_id;

    public EditCartVariantRequest(long idP, long idV, long newVariantId) {
        new_variant_id = newVariantId;
        product_id = idP;
        variant_id = idV;
    }
}
