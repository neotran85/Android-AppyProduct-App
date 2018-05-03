package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AppyAddress extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public int id;

    @Expose
    @SerializedName("place_id")
    @ColumnInfo(name = "place_id")
    public String place_id;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public String user_id;

    @Expose
    @SerializedName("west_my")
    @ColumnInfo(name = "west_my")
    public int west_my;

    @Expose
    @SerializedName("indoor_address")
    @ColumnInfo(name = "indoor_address")
    public String indoor_address;

    @Expose
    @SerializedName("outdoor_address")
    @ColumnInfo(name = "outdoor_address")
    public String outdoor_address;

    @Expose
    @SerializedName("address_name")
    @ColumnInfo(name = "address_name")
    public String address_name;

    @Expose
    @SerializedName("recipient_phone_number")
    @ColumnInfo(name = "recipient_phone_number")
    public String recipient_phone_number;

    @Expose
    @SerializedName("company_name")
    @ColumnInfo(name = "company_name")
    public String company_name;

    @Expose
    @SerializedName("recipient_name")
    @ColumnInfo(name = "recipient_name")
    public String recipient_name;

    @Expose
    @SerializedName("city")
    @ColumnInfo(name = "city")
    public String city;

    @Expose
    @SerializedName("country")
    @ColumnInfo(name = "country")
    public String country;

    @Expose
    @SerializedName("state")
    @ColumnInfo(name = "state")
    public String state;

    @Expose
    @SerializedName("post_code")
    @ColumnInfo(name = "post_code")
    public String post_code;

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String created_at;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updated_at;

    @Expose
    @SerializedName("is_default")
    @ColumnInfo(name = "is_default")
    public int is_default;

    @Expose
    @SerializedName("longitude")
    @ColumnInfo(name = "longitude")
    public double longitude;

    @Expose
    @SerializedName("latitude")
    @ColumnInfo(name = "latitude")
    public double latitude;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    public void updateFrom(AppyAddress newAddress) {
        if (newAddress != null) {
            state = newAddress.state;
            post_code = newAddress.post_code;
            city = newAddress.city;
            country = newAddress.country;
            address_name = newAddress.address_name;
            outdoor_address = newAddress.outdoor_address;
            indoor_address = newAddress.indoor_address;
            created_at = newAddress.created_at;
            updated_at = newAddress.updated_at;
            is_default = newAddress.is_default;
            west_my = newAddress.west_my;
            company_name = newAddress.company_name;
            recipient_name = newAddress.recipient_name;
            recipient_phone_number = newAddress.recipient_phone_number;
        }
    }

    public String getAddressText() {
        String postCode = (post_code.length() > 0) ? "Post Code: " + post_code : "";
        String add = DataUtils.joinStrings(", ",
                indoor_address, outdoor_address, city, state, postCode);
        add = add.replace(" ,", " ");
        return add;
    }
}
