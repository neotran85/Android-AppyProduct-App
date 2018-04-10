package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Supplier extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public String id;

    @Expose
    @SerializedName("company_name")
    @ColumnInfo(name = "company_name")
    public String company_name;

    @Expose
    @SerializedName("contact_title")
    @ColumnInfo(name = "contact_title")
    public String contact_title;

    @Expose
    @SerializedName("logo_path")
    @ColumnInfo(name = "logo_path")
    public String logo_path;

    @Expose
    @SerializedName("address1")
    @ColumnInfo(name = "address1")
    public Address address1;

    @Expose
    @SerializedName("address2")
    @ColumnInfo(name = "address2")
    public Address address2;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

}
