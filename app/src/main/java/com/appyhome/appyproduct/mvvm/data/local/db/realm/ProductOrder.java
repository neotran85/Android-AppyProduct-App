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
    @SerializedName("customer_name")
    @ColumnInfo(name = "customer_name")
    public String customer_name;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public long user_id;

    @Expose
    @SerializedName("price")
    @ColumnInfo(name = "price")
    public double price;

    @Expose
    @SerializedName("shipping")
    @ColumnInfo(name = "shipping")
    public double shipping;

    @Expose
    @SerializedName("discount")
    @ColumnInfo(name = "discount")
    public double discount;

    @Expose
    @SerializedName("status")
    @ColumnInfo(name = "status")
    public String status;

    @Expose
    @SerializedName("payment_method")
    @ColumnInfo(name = "payment_method")
    public String payment_method;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    @Expose
    @SerializedName("content")
    @ColumnInfo(name = "content")
    public RealmList<ProductBought> items;
}
