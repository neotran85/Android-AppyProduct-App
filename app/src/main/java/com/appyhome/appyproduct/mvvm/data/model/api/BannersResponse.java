package com.appyhome.appyproduct.mvvm.data.model.api;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BannersResponse {
    @Expose
    @SerializedName("message")
    public BannerResponse[] message;

    @Expose
    @SerializedName("code")
    public String code;
}
