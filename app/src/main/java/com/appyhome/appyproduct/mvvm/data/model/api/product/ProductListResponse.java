package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;


public class ProductListResponse {
    @Expose
    @SerializedName("message")
    public RealmList<Product> message;

    @Expose
    @SerializedName("code")
    public String code;
}
