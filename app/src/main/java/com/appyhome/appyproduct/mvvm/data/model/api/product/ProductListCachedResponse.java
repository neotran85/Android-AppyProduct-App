package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCached;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;


public class ProductListCachedResponse {
    @Expose
    @SerializedName("message")
    public RealmList<ProductCached> message;

    @Expose
    @SerializedName("code")
    public String code;
}
