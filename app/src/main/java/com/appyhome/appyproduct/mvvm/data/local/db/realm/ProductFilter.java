package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductFilter extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public String user_id;


    @Expose
    @SerializedName("shipping_from")
    @ColumnInfo(name = "shipping_from")
    public String shipping_from;

    @Expose
    @SerializedName("discount")
    @ColumnInfo(name = "discount")
    public String discount;

    @Expose
    @SerializedName("price_min")
    @ColumnInfo(name = "price_min")
    public String price_min;

    @Expose
    @SerializedName("price_max")
    @ColumnInfo(name = "price_max")
    public String price_max;

    @Expose
    @SerializedName("rating")
    @ColumnInfo(name = "rating")
    public float rating;
}
