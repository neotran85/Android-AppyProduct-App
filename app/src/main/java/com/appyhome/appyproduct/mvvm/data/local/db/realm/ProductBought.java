package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductBought extends RealmObject implements Serializable {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("seller_id")
    @ColumnInfo(name = "seller_id")
    public long seller_id;

    @Expose
    @SerializedName("seller_name")
    @ColumnInfo(name = "seller_name")
    public String seller_name;

    @Expose
    @SerializedName("rate")
    @ColumnInfo(name = "rate")
    public float rate;

    @Expose
    @SerializedName("rate_count")
    @ColumnInfo(name = "rate_count")
    public int rate_count;

    @Expose
    @SerializedName("discount")
    @ColumnInfo(name = "discount")
    public int discount;

    @Expose
    @SerializedName("favorite_count")
    @ColumnInfo(name = "favorite_count")
    public int favorite_count;

    @Expose
    @SerializedName("category_id")
    @ColumnInfo(name = "category_id")
    public long category_id;

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
    public long shipping_type_id;

    @Expose
    @SerializedName("pricing_scheme_id")
    @ColumnInfo(name = "pricing_scheme_id")
    public long pricing_scheme_id;

    @Expose
    @SerializedName("tax_class_id")
    @ColumnInfo(name = "tax_class_id")
    public long tax_class_id;

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

    @Expose
    @SerializedName("lowest_price")
    @ColumnInfo(name = "lowest_price")
    public double lowest_price;

    @Expose
    @SerializedName("like")
    @ColumnInfo(name = "like")
    public long like;

    @Expose
    @SerializedName("cached")
    @ColumnInfo(name = "cached")
    public long cached = 0;

    @Expose
    @SerializedName("wishlist")
    @ColumnInfo(name = "wishlist")
    public String wishlist;

    @Expose
    @SerializedName("related")
    @ColumnInfo(name = "related")
    public String related;

    @Expose
    @SerializedName("cart_id")
    @ColumnInfo(name = "cart_id")
    public long cart_id;

    @Expose
    @SerializedName("quantity")
    @ColumnInfo(name = "quantity")
    public int quantity;

    @Expose
    @SerializedName("year_ext_warranty")
    @ColumnInfo(name = "year_ext_warranty")
    public int year_ext_warranty;

    @Expose
    @SerializedName("ext_warranty")
    @ColumnInfo(name = "ext_warranty")
    public int ext_warranty;

    @Expose
    @SerializedName("intro")
    @ColumnInfo(name = "intro")
    public String intro;

    @Expose
    @SerializedName("features")
    @ColumnInfo(name = "features")
    public RealmList<String> features;

    @Expose
    @SerializedName("descr_images")
    @ColumnInfo(name = "descr_images")
    public RealmList<String> descr_images;

    @Expose
    @SerializedName("warranty")
    @ColumnInfo(name = "warranty")
    public String warranty;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;
}
