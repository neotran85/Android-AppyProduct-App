package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductBought extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("order_id")
    @ColumnInfo(name = "order_id")
    public long order_id;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public long user_id;

    @Expose
    @SerializedName("product_id")
    @ColumnInfo(name = "product_id")
    public long product_id;

    @Expose
    @SerializedName("variant_id")
    @ColumnInfo(name = "variant_id")
    public long variant_id;

    @Expose
    @SerializedName("quantity")
    @ColumnInfo(name = "quantity")
    public long quantity;

    @Expose
    @SerializedName("card_id")
    @ColumnInfo(name = "card_id")
    public long card_id;

    @Expose
    @SerializedName("price")
    @ColumnInfo(name = "price")
    public double price;

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String created_at;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updated_at;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;
}
