package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddShippingAddressRequest {
    @Expose
    @SerializedName("address_name")
    public String address_name;

    @Expose
    @SerializedName("outdoor_address")
    public String outdoor_address;

    @Expose
    @SerializedName("indoor_address")
    public String indoor_address;

    @Expose
    @SerializedName("city")
    public String city;

    @Expose
    @SerializedName("post_code")
    public String post_code;

    @Expose
    @SerializedName("state")
    public String state;

    @Expose
    @SerializedName("recipient_name")
    public String recipient_name;

    @Expose
    @SerializedName("recipient_phone_number")
    public String recipient_phone_number;

    @Expose
    @SerializedName("company_name")
    public String company_name;

    @Expose
    @SerializedName("is_default")
    public int is_default;

    public AddShippingAddressRequest(AppyAddress address) {
        address_name = address.address_name;
        outdoor_address = address.outdoor_address;
        indoor_address = address.indoor_address;
        city = address.city;
        state = address.state;
        post_code = address.post_code;
        recipient_name = address.recipient_name;
        recipient_phone_number = address.recipient_phone_number;
        company_name = address.company_name;
        is_default = address.is_default;
    }
}
