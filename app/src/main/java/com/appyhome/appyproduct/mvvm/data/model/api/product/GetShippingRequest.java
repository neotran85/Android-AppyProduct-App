package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetShippingRequest {
    @Expose
    @SerializedName("product_id")
    public int product_id;

    @Expose
    @SerializedName("cust_mode")
    public String cust_mode;

    @Expose
    @SerializedName("cust_postcode")
    public String cust_postcode;

    @Expose
    @SerializedName("weight")
    public float weight;

    public GetShippingRequest(int productId, String custMode, String custPostCode, float weightProduct) {
        product_id = productId;
        cust_mode = custMode;
        cust_postcode = custPostCode;
        weight = weightProduct;
    }

}
