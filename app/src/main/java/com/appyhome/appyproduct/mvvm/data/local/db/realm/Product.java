package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import io.realm.annotations.PrimaryKey;

public class Product extends SubCategory {
    @PrimaryKey
    private String id;
    private double price;
    private String thumbnailURL;
    private String dateAdded;
    private long supplierId;
    private int amountStock;
    private float rating;
    private int reviewCount;
}
