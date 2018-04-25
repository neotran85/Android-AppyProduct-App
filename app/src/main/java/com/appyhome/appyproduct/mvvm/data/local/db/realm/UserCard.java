package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserCard extends RealmObject {
    public static final int VISA_CREDIT = 1;
    public static final int VISA_DEBIT = 2;
    public static final int MASTER = 3;

    @ColumnInfo(name = "id")
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "user_id")
    public String user_id;

    @ColumnInfo(name = "card_type")
    public int card_type;

    @ColumnInfo(name = "card_name")
    public String card_name;

    @ColumnInfo(name = "card_number")
    public String card_number;

    @ColumnInfo(name = "expiration_month")
    public int expiration_month;

    @ColumnInfo(name = "expiration_year")
    public int expiration_year;
}
