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
    public boolean cached;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    public String user_id;
}
