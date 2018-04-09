package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCached;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;


public class ProductCartResponse {
    @Expose
    @SerializedName("seller_id")
    public int seller_id;

    @Expose
    @SerializedName("product_id")
    public int product_id;

    @Expose
    @SerializedName("seller_name")
    public String seller_name;

    @Expose
    @SerializedName("seller_image")
    public String seller_image;

    @Expose
    @SerializedName("product_name")
    public String product_name;

    @Expose
    @SerializedName("product_avatar")
    public String product_avatar;

    @Expose
    @SerializedName("variant_id")
    public int variant_id;

    @Expose
    @SerializedName("variant_name")
    public String variant_name;

    @Expose
    @SerializedName("variant_price")
    public float variant_price;

    @Expose
    @SerializedName("variant_stock_left")
    public int variant_stock_left;

    @Expose
    @SerializedName("quantity")
    public int quantity;
}
