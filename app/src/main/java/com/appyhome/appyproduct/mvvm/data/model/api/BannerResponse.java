package com.appyhome.appyproduct.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BannerResponse {
    @Expose
    @SerializedName("id")
    public String id;

    @Expose
    @SerializedName("name")
    public String name;

    @Expose
    @SerializedName("image")
    public String image;

    @Expose
    @SerializedName("url")
    public String url;

    @Expose
    @SerializedName("active")
    public String active;

    @Expose
    @SerializedName("created_at")
    public String created_at;

    @Expose
    @SerializedName("updated_at")
    public String updated_at;
}
