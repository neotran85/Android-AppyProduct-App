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

    public ProductListRequest(int id, int pageNumber) {
        page = pageNumber;
        categoryId = id;
    }
}
