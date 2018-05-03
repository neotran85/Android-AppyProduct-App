package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddShippingAddressResponse {
    @Expose
    @SerializedName("code")
    public String code;

    @Expose
    @SerializedName("message")
    public String message;

    public boolean isValid() {
        return code != null && code.equals(ApiCode.OK_200) && message != null && message.equals("address_saved");
    }
}
