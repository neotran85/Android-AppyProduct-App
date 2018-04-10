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
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    private String copyString(String value) {
        return value != null ? new String(value.toCharArray()) : null;
    }

    public Product convertToProduct() {
        Product product = new Product();
        product.id = product_id;
        product.lowest_price = variant_price;
        product.avatar_name = copyString(product_avatar);
        product.product_name = copyString(product_name);
        product.more_info = more_info;
        return product;
    }
}
