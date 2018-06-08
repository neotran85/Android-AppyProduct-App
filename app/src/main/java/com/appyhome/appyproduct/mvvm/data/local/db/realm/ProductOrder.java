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
    @SerializedName("order_ref")
    @ColumnInfo(name = "order_ref")
    public String order_ref;

    @Expose
    @SerializedName("promocode")
    @ColumnInfo(name = "promocode")
    public String promocode;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updated_at;

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String created_at;

    @Expose
    @SerializedName("address")
    @ColumnInfo(name = "address")
    public String address;

    @Expose
    @SerializedName("log")
    @ColumnInfo(name = "log")
    public String log;

    @Expose
    @SerializedName("transaction_id")
    @ColumnInfo(name = "transaction_id")
    public long transaction_id;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public long user_id;

    @Expose
    @SerializedName("customer_id")
    @ColumnInfo(name = "customer_id")
    public String customer_id;

    @Expose
    @SerializedName("price")
    @ColumnInfo(name = "price")
    public double price;

    @Expose
    @SerializedName("shipping")
    @ColumnInfo(name = "shipping")
    public double shipping;

    @Expose
    @SerializedName("status")
    @ColumnInfo(name = "status")
    public int status;
    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    @Expose
    @SerializedName("content")
    @ColumnInfo(name = "content")
    public RealmList<ProductBought> items;
}
