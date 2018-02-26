package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {
    @PrimaryKey
    private int id;
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
