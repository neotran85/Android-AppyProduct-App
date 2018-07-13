package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductOrder extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

    @Expose
    @SerializedName("order_ref")
    @ColumnInfo(name = "order_ref")
    public String order_ref;

    @Expose
    @SerializedName("customer_id")
    @ColumnInfo(name = "customer_id")
    public String customer_id;

    @Expose
    @SerializedName("promocode")
    @ColumnInfo(name = "promocode")
    public String promocode;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updated_at;

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String created_at;

    @Expose
    @SerializedName("address")
    @ColumnInfo(name = "address")
    public String address;

    @Expose
    @SerializedName("log")
    @ColumnInfo(name = "log")
    public String log;

    @Expose
    @SerializedName("transaction_id")
    @ColumnInfo(name = "transaction_id")
    public long transaction_id;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public long user_id;

    @Expose
    @SerializedName("price")
    @ColumnInfo(name = "price")
    public double price;

    @Expose
    @SerializedName("shipping")
    @ColumnInfo(name = "shipping")
    public double shipping;

    @Expose
    @SerializedName("status")
    @ColumnInfo(name = "status")
    public int status;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    @Expose
    @SerializedName("items")
    @ColumnInfo(name = "items")
    public RealmList<ProductBought> items;

    public void input(JSONObject object) {
        try {
            items = new RealmList<>();
            this.id = object.getInt("id");
            this.order_ref = object.getString("order_ref");
            this.promocode = object.getString("promocode");
            this.updated_at = object.getString("updated_at");
            this.created_at = object.getString("created_at");
            this.address = object.getString("address");
            this.log = object.getString("log");
            this.transaction_id = object.getLong("transaction_id");
            this.user_id = object.getLong("user_id");
            this.price = object.getDouble("price");
            this.shipping = object.getDouble("shipping");
            this.status = object.getInt("status");
            JSONObject contentData =  object.getJSONObject("content");
            Iterator<String> keys = contentData.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                if(key != null && DataUtils.isNumeric(key)) {
                    JSONArray array = contentData.getJSONArray(key);
                    for(int i = 0; i < array.length(); i++) {
                        ProductBought item = new Gson().fromJson(array.getJSONObject(i).toString(), ProductBought.class);
                        items.add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

}
