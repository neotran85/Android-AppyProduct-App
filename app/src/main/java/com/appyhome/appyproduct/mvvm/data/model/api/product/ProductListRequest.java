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

    @Expose
    @SerializedName("cat_id")
    public String cat_id;


    public ProductListRequest(String categoryIds, String catIds, String keyword, int pageNumber, String t) {
        page = pageNumber;
        cat_id = catIds;
        terms = keyword;
        categoryId = categoryIds;
        type = t;
        sortBy = t;
    }
}
