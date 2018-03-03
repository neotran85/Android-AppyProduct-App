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
    @SerializedName("product_avatar")
    @ColumnInfo(name = "product_avatar")
    public String product_avatar;

    @Expose
    @SerializedName("price")
    @ColumnInfo(name = "price")
    public float price;

    @Expose
    @SerializedName("amount")
    @ColumnInfo(name = "amount")
    public int amount;

    @Expose
    @SerializedName("variation_id")
    @ColumnInfo(name = "variation_id")
    public int variation_id;

    @Expose
    @SerializedName("variation_name")
    @ColumnInfo(name = "variation_name")
    public String variation_name;

    @Expose
    @SerializedName("checked")
    @ColumnInfo(name = "checked")
    public boolean checked;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public String user_id;
}