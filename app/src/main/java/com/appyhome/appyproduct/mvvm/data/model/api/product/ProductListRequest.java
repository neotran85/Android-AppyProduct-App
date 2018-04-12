package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductListRequest {
    @Expose
    @SerializedName("category_id")
    public String categoryId;

    @Expose
    @SerializedName("page")
    public int page;

    @Expose
    @SerializedName("type")
    public String type;

    @Expose
    @SerializedName("sortBy")
    public String sortBy;

    @Expose
    @SerializedName("terms")
    public String terms;


    public ProductListRequest(String categoryIds, String keyword, int pageNumber, String t) {
        page = pageNumber;
        terms = keyword;
        categoryId = categoryIds;
        type = t;
        sortBy = t;
    }
}
