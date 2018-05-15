package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ApiResponse {
    @Expose
    @SerializedName("code")
    public String code;

    @Expose
    @SerializedName("message")
    public Object message;

    public boolean isValid() {
        return code != null && code.equals(ApiCode.OK_200);
    }

    public boolean isEmpty() {
        if (message instanceof String) {
            String str = (String) message;
            return str != null && str.contains("cart_empty");
        }
        return false;
    }

    public boolean isVariantQuantityUpdated() {
        return (message != null && message.toString().equals("variant_quantity_updated"));
    }

    public String getMessage() {
        return message.toString();
    }
}
