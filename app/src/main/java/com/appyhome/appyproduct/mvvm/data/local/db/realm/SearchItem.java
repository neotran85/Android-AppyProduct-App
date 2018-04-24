package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SearchItem extends RealmObject {
    @Expose
    @SerializedName("content")
    @ColumnInfo(name = "content")
    @PrimaryKey
    public String content;

    @Expose
    @SerializedName("cached")
    @ColumnInfo(name = "cached")
    public boolean cached = false;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public String user_id;

    @Expose
    @SerializedName("time_added")
    @ColumnInfo(name = "time_added")
    public long time_added;

    @Expose
    @SerializedName("topics")
    @ColumnInfo(name = "topics")
    public String topics;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;
}
