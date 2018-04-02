package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductListRequest {
    @Expose
    @SerializedName("category_id")
    public int categoryId;

    @Expose
    @SerializedName("page")
    public int page;

    @Expose
    @SerializedName("type")
    public String type;

    @Expose
    @SerializedName("keyword")
    public String keyword;

    public ProductListRequest(String kw, int pageNumber, String t) {
        page = pageNumber;
        keyword = kw;
        type = t;
    }

    public ProductListRequest(int id, int pageNumber, String t) {
        page = pageNumber;
        categoryId = id;
        type = t;
    }
}
