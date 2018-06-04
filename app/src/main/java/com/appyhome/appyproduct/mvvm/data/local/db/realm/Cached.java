package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cached extends RealmObject {
    @Expose
    @SerializedName("key")
    @ColumnInfo(name = "key")
    @PrimaryKey
    public String key;

    @Expose
    @SerializedName("data")
    @ColumnInfo(name = "data")
    public String data;
}
