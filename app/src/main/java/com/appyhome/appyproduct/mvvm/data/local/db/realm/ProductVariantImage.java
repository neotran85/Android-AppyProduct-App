package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductVariantImage extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("seller_name")
    @ColumnInfo(name = "seller_name")
    public String seller_name;

    @Expose
    @SerializedName("filename")
    @ColumnInfo(name = "filename")
    public String filename;

    @Expose
    @SerializedName("product_id")
    @ColumnInfo(name = "product_id")
    public int product_id;

    @Expose
    @SerializedName("variant_id")
    @ColumnInfo(name = "variant_id")
    public int variant_id;

    @Expose
    @SerializedName("URL")
    @ColumnInfo(name = "URL")
    public String URL;

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
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

}
