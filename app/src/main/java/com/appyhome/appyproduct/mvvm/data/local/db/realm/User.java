package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    public String id;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "username")
    public String userName;

    @ColumnInfo(name = "phone_number")
    public String phoneNumber;

    @ColumnInfo(name = "email")
    public String emailAddress;

    @ColumnInfo(name = "address")
    public AppyAddress address;

    @ColumnInfo(name = "shipping_address")
    public AppyAddress shippingAddress;

    @ColumnInfo(name = "birthday")
    public String birthday;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "token")
    public String token;

    @ColumnInfo(name = "avatar_path")
    public String avatarPath;

    @ColumnInfo(name = "more_info")
    public String more_info;
}
