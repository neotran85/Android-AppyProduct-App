package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CartShippingFee extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("cart_id")
    @ColumnInfo(name = "cart_id")
    public long cart_id;

    @Expose
    @SerializedName("fee")
    @ColumnInfo(name = "fee")
    public String fee;

    @Expose
    @SerializedName("address_id")
    @ColumnInfo(name = "address_id")
    public String address_id;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public String user_id;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;
}
