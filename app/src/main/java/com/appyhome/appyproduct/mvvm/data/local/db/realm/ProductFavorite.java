package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductFavorite extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

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
    @SerializedName("variant_id")
    @ColumnInfo(name = "variant_id")
    public int variant_id;

    @Expose
    @SerializedName("variant_price")
    @ColumnInfo(name = "variant_price")
    public float variant_price;

    @Expose
    @SerializedName("variant_stock_left")
    @ColumnInfo(name = "variant_stock_left")
    public float variant_stock_left;

    @Expose
    @SerializedName("variant_model_id")
    @ColumnInfo(name = "variant_model_id")
    public String variant_model_id;

    @Expose
    @SerializedName("variant_name")
    @ColumnInfo(name = "variant_name")
    public String variant_name;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public String user_id;

    @Expose
    @SerializedName("favorite_count")
    @ColumnInfo(name = "favorite_count")
    public int favorite_count;

    @Expose
    @SerializedName("rating")
    @ColumnInfo(name = "rating")
    public float rating;

    @Expose
    @SerializedName("rating_count")
    @ColumnInfo(name = "rating_count")
    public int rating_count;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    public ProductFavorite() {}

    public ProductFavorite(ProductCached product) {
        product_id = product.id;
        variant_price = product.lowest_price;
        product_avatar = product.avatar_name;
        product_name = product.product_name;
        more_info = product.more_info;
        favorite_count = product.like;
        rating = product.rate;
        rating_count = product.rate_count;
    }

    public Product toProduct() {
        Product product = new Product();
        product.id = product_id;
        product.lowest_price = variant_price;
        product.avatar_name = product_avatar;
        product.product_name = product_name;
        product.more_info = more_info;
        product.like = favorite_count;
        product.rate = rating;
        product.rate_count = rating_count;
        return product;
    }
}
