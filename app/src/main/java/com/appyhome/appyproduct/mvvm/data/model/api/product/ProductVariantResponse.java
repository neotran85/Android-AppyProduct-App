package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductVariantResponse {
    @Expose
    @SerializedName("message")
    public ProductVariant[] message;

    @Expose
    @SerializedName("code")
    public String code;
}
