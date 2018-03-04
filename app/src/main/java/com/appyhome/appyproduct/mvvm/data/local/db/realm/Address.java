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
    @SerializedName("address")
    @ColumnInfo(name = "address")
    public String address;

    @Expose
    @SerializedName("phone_number")
    @ColumnInfo(name = "phone_number")
    public String phone_number;

    @Expose
    @SerializedName("customer_name")
    @ColumnInfo(name = "customer_name")
    public String customer_name;

    @Expose
    @SerializedName("customer_id")
    @ColumnInfo(name = "customer_id")
    public String customer_id;

    @Expose
    @SerializedName("is_default")
    @ColumnInfo(name = "is_default")
    public boolean is_default;

}
