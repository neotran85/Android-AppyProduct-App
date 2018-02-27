package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    private int id;

    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private double price;

    private String thumbnailURL;
    private String dateAdded;
    private long supplierId;
    private int amountStock;
    private float rating;
    private int reviewCount;
    private String title;
    private String description;
    private String imageURL;
    private long idCategory;
}
