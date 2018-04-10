package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductSub extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public int id;

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "name")
    public String name;

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    public String description;

    @Expose
    @SerializedName("id_category")
    @ColumnInfo(name = "id_category")
    public int id_category;

    @Expose
    @SerializedName("thumbnail")
    @ColumnInfo(name = "thumbnail")
    public String thumbnail;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;
}
