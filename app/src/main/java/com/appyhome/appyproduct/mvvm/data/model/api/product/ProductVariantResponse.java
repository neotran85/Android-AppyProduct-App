package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;


public class ProductVariantResponse {
    @Expose
    @SerializedName("message")
    public RealmList<ProductVariant> message;

    @Expose
    @SerializedName("code")
    public String code;

    public boolean isValid() {
        return code != null && code.equals(ApiCode.OK_200);
    }
}
