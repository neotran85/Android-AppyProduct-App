package com.appyhome.appyproduct.mvvm.data.model.db;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppyServiceCategory {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    public String id;

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "name")
    public String name;
}
