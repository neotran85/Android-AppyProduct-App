package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductCart extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("seller_id")
    @ColumnInfo(name = "seller_id")
    public int seller_id;

    @Expose
    @SerializedName("seller_name")
    @ColumnInfo(name = "seller_name")
    public String seller_name;

    @Expose
    @SerializedName("seller_avatar")
    @ColumnInfo(name = "seller_avatar")
    public String seller_avatar;

    @Expose
    @SerializedName("product_id")
    @ColumnInfo(name = "product_id")
    public int product_id;

    @Expose
    @SerializedName("product_name")
    @ColumnInfo(name = "product_name")
    public String product_name;

    @Expose
    @SerializedName("product_image")
    @ColumnInfo(name = "product_image")
    public String product_image;

    @Expose
    @SerializedName("price")
    @ColumnInfo(name = "price")
    public float price;

    @Expose
    @SerializedName("amount")
    @ColumnInfo(name = "amount")
    public int amount;

    @Expose
    @SerializedName("checked")
    @ColumnInfo(name = "checked")
    public boolean checked;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public String user_id;

    @Expose
    @SerializedName("order_id")
    @ColumnInfo(name = "order_id")
    public long order_id;

    @Expose
    @SerializedName("variant_id")
    @ColumnInfo(name = "variant_id")
    public int variant_id;

    @Expose
    @SerializedName("variant_model_id")
    @ColumnInfo(name = "variant_model_id")
    public String variant_model_id;

    @Expose
    @SerializedName("variant_name")
    @ColumnInfo(name = "variant_name")
    public String variant_name;

    @Expose
    @SerializedName("variant_stock")
    @ColumnInfo(name = "variant_stock")
    public int variant_stock;

    @Expose
    @SerializedName("time_added")
    @ColumnInfo(name = "time_added")
    public long time_added;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;
}
