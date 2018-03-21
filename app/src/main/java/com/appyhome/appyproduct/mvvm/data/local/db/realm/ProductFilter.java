package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductFilter extends RealmObject {
    @PrimaryKey
    public long id;

    public String user_id;

    public String shipping_from;

    public String discount;

    public float price_min;

    public float price_max;

    public float rating;
}
