package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductCategory extends RealmObject {
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
    @SerializedName("thumbnail")
    @ColumnInfo(name = "thumbnail")
    public String thumbnail;

    @Expose
    @SerializedName("id_topic")
    @ColumnInfo(name = "id_topic")
    public int id_topic;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    @Expose
    @SerializedName("sub_ids")
    @ColumnInfo(name = "sub_ids")
    public String sub_ids;

    public ArrayList<Integer> getSubIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        String[] result = TextUtils.split(sub_ids, ",");
        for (String str : result) {
            Integer id = Integer.valueOf(str);
            ids.add(id);
        }
        return ids;
    }
}
