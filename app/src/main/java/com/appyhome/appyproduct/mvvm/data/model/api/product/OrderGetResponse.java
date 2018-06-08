package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;


public class OrderGetResponse {
    @Expose
    @SerializedName("code")
    public String code;

    @Expose
    @SerializedName("message")
    public RealmList<ProductOrder> message;

    public boolean isValid() {
        return code != null && code.equals(ApiCode.OK_200);
    }
}
