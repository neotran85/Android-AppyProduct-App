package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetShippingResponse {
    @Expose
    @SerializedName("code")
    public String code;

    @Expose
    @SerializedName("message")
    public float price;

    public boolean isValid() {
        return code != null && code.equals(ApiCode.OK_200);
    }

}
