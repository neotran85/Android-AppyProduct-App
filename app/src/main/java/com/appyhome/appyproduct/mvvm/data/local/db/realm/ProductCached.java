package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductCached extends RealmObject implements Serializable {
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
    @SerializedName("time_db_added")
    @ColumnInfo(name = "time_db_added")
    public long time_db_added;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    private String copyString(String value) {
        return value != null ? new String(value.toCharArray()) : null;
    }

    public Product convertToProduct() {
        Product product = new Product();
        product.id = id;
        product.stock_location = copyString(stock_location);
        product.lowest_price = lowest_price;
        product.avatar_name = copyString(avatar_name);
        product.category_id = category_id;
        product.country_manu = copyString(country_manu);
        product.created_at = copyString(created_at);
        product.description = copyString(description);
        product.discount = discount;
        product.enabled = enabled;
        product.favorite_count = favorite_count;
        product.flag = copyString(flag);
        product.like = like;
        product.pricing_scheme_id = pricing_scheme_id;
        product.product_name = copyString(product_name);
        product.rate = rate;
        product.rate_count = rate_count;
        product.seller_id = seller_id;
        product.seller_name = copyString(seller_name);
        product.shipping_type_id = shipping_type_id;
        product.sort_order = sort_order;
        product.tax_class_id = tax_class_id;
        product.updated_at = copyString(updated_at);
        product.more_info = more_info;
        return product;
    }
}
