package com.appyhome.appyproduct.mvvm.data.model.db;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppyService {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    public String id;

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "name")
    public String name;

    @Expose
    @SerializedName("category")
    @ColumnInfo(name = "category")
    public String category;

    @Expose
    @SerializedName("price")
    @ColumnInfo(name = "price")
    public String price;

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    public String description;

    @Expose
    @SerializedName("detail")
    @ColumnInfo(name = "detail")
    public String detail;
}
