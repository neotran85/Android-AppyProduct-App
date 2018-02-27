package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.appyhome.appyproduct.mvvm.data.remote.ApiCode;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class ProductListResponse {
    @Expose
    @SerializedName("message")
    public ProductItemResponse[] message;

    @Expose
    @SerializedName("code")
    public String code;

    public class ProductItemResponse {
        @Expose
        @SerializedName("id")
        public int id;

        @Expose
        @SerializedName("seller_id")
        public int idSeller;

        @Expose
        @SerializedName("category_id")
        public int idCategory;

        @Expose
        @SerializedName("product_name")
        public String productName;

        @Expose
        @SerializedName("stock_location")
        public String stockLocation;

        @Expose
        @SerializedName("country_manu")
        public String country;

        @Expose
        @SerializedName("avatar_name")
        public String avatarName;

        @Expose
        @SerializedName("shipping_type_id")
        public int idTypeShipping;

        @Expose
        @SerializedName("pricing_scheme_id")
        public int idPricingScheme;

        @Expose
        @SerializedName("tax_class_id")
        public int idTaxClass;

        @Expose
        @SerializedName("enabled")
        public int enabled;

        @Expose
        @SerializedName("description")
        public String description;

        @Expose
        @SerializedName("sort_order")
        public int sortOrder;

        @Expose
        @SerializedName("flag")
        public String flag;

        @Expose
        @SerializedName("created_at")
        public String createdAt;

        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
    }
}
