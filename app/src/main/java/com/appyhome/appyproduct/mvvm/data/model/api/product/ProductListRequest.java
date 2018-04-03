package com.appyhome.appyproduct.mvvm.data.model.api.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductListRequest {
    @Expose
    @SerializedName("category_id")
    public int categoryId = 0;

    @Expose
    @SerializedName("page")
    public int page;

    @Expose
    @SerializedName("type")
    public String type;

    @Expose
    @SerializedName("name")
    public String name;

    public ProductListRequest(Object data, int pageNumber, String t) {
        page = pageNumber;
        if (data instanceof String)
            name = (String) data;
        else if (data instanceof Integer)
            categoryId = (Integer) data;
        type = t;
    }
}
