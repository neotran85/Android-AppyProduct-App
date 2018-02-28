package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public int id;

    @Expose
    @SerializedName("seller_id")
    @ColumnInfo(name = "seller_id")
    public int seller_id;

    @Expose
    @SerializedName("category_id")
    @ColumnInfo(name = "category_id")
    public int category_id;

    @Expose
    @SerializedName("product_name")
    @ColumnInfo(name = "product_name")
    public String product_name;

    @Expose
    @SerializedName("stock_location")
    @ColumnInfo(name = "stock_location")
    public String stock_location;

    @Expose
    @SerializedName("country_manu")
    @ColumnInfo(name = "country_manu")
    public String country_manu;

    @Expose
    @SerializedName("avatar_name")
    @ColumnInfo(name = "avatar_name")
    public String avatar_name;

    @Expose
    @SerializedName("shipping_type_id")
    @ColumnInfo(name = "shipping_type_id")
    public int shipping_type_id;

    @Expose
    @SerializedName("pricing_scheme_id")
    @ColumnInfo(name = "pricing_scheme_id")
    public int pricing_scheme_id;

    @Expose
    @SerializedName("tax_class_id")
    @ColumnInfo(name = "tax_class_id")
    public int tax_class_id;

    @Expose
    @SerializedName("enabled")
    @ColumnInfo(name = "enabled")
    public int enabled;

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    public String description;

    @Expose
    @SerializedName("sort_order")
    @ColumnInfo(name = "sort_order")
    public int sort_order;

    @Expose
    @SerializedName("flag")
    @ColumnInfo(name = "flag")
    public String flag;

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String created_at;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updated_at;
}
