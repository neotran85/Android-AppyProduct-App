package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductOrder extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("customer_name")
    @ColumnInfo(name = "customer_name")
    public String customer_name;

    @Expose
    @SerializedName("customer_id")
    @ColumnInfo(name = "customer_id")
    public String customer_id;

    @Expose
    @SerializedName("total_cost")
    @ColumnInfo(name = "total_cost")
    public float total_cost;

    @Expose
    @SerializedName("discount")
    @ColumnInfo(name = "discount")
    public float discount;

    @Expose
    @SerializedName("payment_method")
    @ColumnInfo(name = "payment_method")
    public String payment_method;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    public RealmList<ProductCart> cart;

    public Address shipping_address;

}
