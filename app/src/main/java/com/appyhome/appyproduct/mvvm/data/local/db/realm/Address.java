package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Address extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

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
    public String west_my;

    @Expose
    @SerializedName("address_content")
    @ColumnInfo(name = "address_content")
    public String address_content;

    @Expose
    @SerializedName("address_name")
    @ColumnInfo(name = "address_name")
    public String address_name;

    @Expose
    @SerializedName("recipient_phonenumber")
    @ColumnInfo(name = "recipient_phonenumber")
    public String recipient_phonenumber;

    @Expose
    @SerializedName("company_name")
    @ColumnInfo(name = "company_name")
    public String company_name;

    @Expose
    @SerializedName("recipient_name")
    @ColumnInfo(name = "recipient_name")
    public String recipient_name;

    @Expose
    @SerializedName("avatar")
    @ColumnInfo(name = "avatar")
    public String avatar;

    @Expose
    @SerializedName("unit")
    @ColumnInfo(name = "unit")
    public String unit;

    @Expose
    @SerializedName("city")
    @ColumnInfo(name = "city")
    public String city;

    @Expose
    @SerializedName("state")
    @ColumnInfo(name = "state")
    public String state;

    @Expose
    @SerializedName("street")
    @ColumnInfo(name = "street")
    public String street;

    @Expose
    @SerializedName("post_code")
    @ColumnInfo(name = "post_code")
    public String post_code;

    @Expose
    @SerializedName("is_default")
    @ColumnInfo(name = "is_default")
    public boolean is_default;

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
}
