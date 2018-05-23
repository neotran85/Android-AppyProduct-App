package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductFavorite extends RealmObject {
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
    @SerializedName("product_avatar")
    @ColumnInfo(name = "product_avatar")
    public String product_avatar;

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    public String description;

    @Expose
    @SerializedName("country_manu")
    @ColumnInfo(name = "country_manu")
    public String country_manu;

    @Expose
    @SerializedName("seller_name")
    @ColumnInfo(name = "seller_name")
    public String seller_name;

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
    @SerializedName("sort_order")
    @ColumnInfo(name = "sort_order")
    public int sort_order;

    @Expose
    @SerializedName("lowest_price")
    @ColumnInfo(name = "lowest_price")
    public double lowest_price;

    @Expose
    @SerializedName("avatar_name")
    @ColumnInfo(name = "avatar_name")
    public String avatar_name;

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
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String created_at;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updated_at;

    @Expose
    @SerializedName("updated_date")
    @ColumnInfo(name = "updated_date")
    public long updated_date;

    @Expose
    @SerializedName("warranty")
    @ColumnInfo(name = "warranty")
    public String warranty;

    @Expose
    @SerializedName("flag")
    @ColumnInfo(name = "flag")
    public String flag;

    @Expose
    @SerializedName("like")
    @ColumnInfo(name = "like")
    public long like;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    public ProductFavorite() {
    }

    public ProductFavorite(String userId, ProductCached product) {
        id = System.currentTimeMillis();
        user_id = userId;
        if (product != null) {
            id = product.id;
            product_name = product.product_name;
            more_info = product.more_info;
            favorite_count = product.favorite_count;
            rating = product.rate;
            rating_count = product.rate_count;
            avatar_name = product.avatar_name;
            seller_id = product.seller_id;
            like = product.like;
            flag = product.flag;
            created_at = product.created_at;
            shipping_type_id = product.shipping_type_id;
            tax_class_id = product.tax_class_id;
            sort_order = product.sort_order;
            lowest_price = product.lowest_price;
            description = product.description;
            pricing_scheme_id = product.pricing_scheme_id;
            category_id = product.category_id;
            Date date = DataUtils.toDate(updated_at);
            if (date != null) updated_date = date.getTime();
        }
    }

    public void setUpdated_date() {
        Date date = DataUtils.toDate(updated_at);
        if (date != null) updated_date = date.getTime();
    }

    public Product toProduct() {
        Product product = new Product();
        product.id = id;
        product.product_name = product_name;
        product.more_info = more_info;
        product.favorite_count = favorite_count;
        product.avatar_name = avatar_name;
        product.seller_id = seller_id;
        product.like = like;
        product.flag = flag;
        product.created_at = created_at;
        product.shipping_type_id = shipping_type_id;
        product.tax_class_id = tax_class_id;
        product.sort_order = sort_order;
        product.lowest_price = lowest_price;
        product.description = description;
        product.pricing_scheme_id = pricing_scheme_id;
        product.category_id = category_id;
        return product;
    }
}
