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
    private long id;

    @Expose
    @SerializedName("unit")
    @ColumnInfo(name = "unit")
    private String unit;

    @Expose
    @SerializedName("place_id")
    @ColumnInfo(name = "place_id")
    private String place_id;

    @Expose
    @SerializedName("areaLine1")
    @ColumnInfo(name = "areaLine1")
    private String areaLine1;

    @Expose
    @SerializedName("areaLine2")
    @ColumnInfo(name = "areaLine2")
    private String areaLine2;

    @Expose
    @SerializedName("street_name")
    @ColumnInfo(name = "street_name")
    private String street_name;

    @Expose
    @SerializedName("city")
    @ColumnInfo(name = "city")
    private String city;

    @Expose
    @SerializedName("country")
    @ColumnInfo(name = "country")
    private String country;

    @Expose
    @SerializedName("post_code")
    @ColumnInfo(name = "post_code")
    private String post_code;

    @Expose
    @SerializedName("phone_number")
    @ColumnInfo(name = "phone_number")
    private String phone_number;

    @Expose
    @SerializedName("customer_name")
    @ColumnInfo(name = "customer_name")
    private String customer_name;

    @Expose
    @SerializedName("customer_id")
    @ColumnInfo(name = "customer_id")
    private String customer_id;

}
