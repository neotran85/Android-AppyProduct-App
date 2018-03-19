package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductListRequest {
    @Expose
    @SerializedName("category_id")
    private int categoryId;

    @Expose
    @SerializedName("page")
    private int page;

    @Expose
    @SerializedName("type")
    private String type;

    public ProductListRequest(int id, int pageNumber, String t) {
        page = pageNumber;
        categoryId = id;
        type = t;
    }
}
