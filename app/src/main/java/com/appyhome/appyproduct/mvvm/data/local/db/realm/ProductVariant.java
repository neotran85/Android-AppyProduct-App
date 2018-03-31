package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductVariant extends RealmObject {
    @Expose
    @SerializedName("model_id")
    @ColumnInfo(name = "model_id")
    @PrimaryKey
    public String model_id;

    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    public int id;

    @Expose
    @SerializedName("product_id")
    @ColumnInfo(name = "product_id")
    public int product_id;

    @Expose
    @SerializedName("variant_type")
    @ColumnInfo(name = "variant_type")
    public String variant_type;

    @Expose
    @SerializedName("variant_name")
    @ColumnInfo(name = "variant_name")
    public String variant_name;

    @Expose
    @SerializedName("sku")
    @ColumnInfo(name = "sku")
    public String sku;

    @Expose
    @SerializedName("quantity")
    @ColumnInfo(name = "quantity")
    public int quantity;

    @Expose
    @SerializedName("price")
    @ColumnInfo(name = "price")
    public float price;

    @Expose
    @SerializedName("stroke_price")
    @ColumnInfo(name = "stroke_price")
    public float stroke_price;

    @Expose
    @SerializedName("loyalty_id")
    @ColumnInfo(name = "loyalty_id")
    public int loyalty_id;

    @Expose
    @SerializedName("flag")
    @ColumnInfo(name = "flag")
    public String flag;

    @Expose
    @SerializedName("date_available")
    @ColumnInfo(name = "date_available")
    public String date_available;

    @Expose
    @SerializedName("date_unavailable")
    @ColumnInfo(name = "date_unavailable")
    public String date_unavailable;

    @Expose
    @SerializedName("weight")
    @ColumnInfo(name = "weight")
    public double weight;

    @Expose
    @SerializedName("length")
    @ColumnInfo(name = "length")
    public float length;

    @Expose
    @SerializedName("width")
    @ColumnInfo(name = "width")
    public float width;

    @Expose
    @SerializedName("height")
    @ColumnInfo(name = "height")
    public float height;

    @Expose
    @SerializedName("seo_keywords")
    @ColumnInfo(name = "seo_keywords")
    public String seo_keywords;

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    public String description;

    @Expose
    @SerializedName("stock_status")
    @ColumnInfo(name = "stock_status")
    public String stock_status;

    @Expose
    @SerializedName("set_id")
    @ColumnInfo(name = "set_id")
    public int set_id;

    @Expose
    @SerializedName("sort_order")
    @ColumnInfo(name = "sort_order")
    public int sort_order;


    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String created_at;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updated_at;

    @Expose
    @SerializedName("images")
    public RealmList<ProductVariantImage> images;
}
