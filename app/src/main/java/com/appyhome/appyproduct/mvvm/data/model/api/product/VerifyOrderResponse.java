package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VerifyOrderResponse {
    @Expose
    @SerializedName("code")
    public String code;

    @Expose
    @SerializedName("message")
    public Object message;

    public boolean isValid() {
        return code != null && code.equals(ApiCode.OK_200);
    }

    public String getMessage() {
        return message.toString();
    }
}
