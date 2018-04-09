package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddToCartResponse {
    @Expose
    @SerializedName("code")
    public String  code;

    @Expose
    @SerializedName("message")
    public String message;

}
