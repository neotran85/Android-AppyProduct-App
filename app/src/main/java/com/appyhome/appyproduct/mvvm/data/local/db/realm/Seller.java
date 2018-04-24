package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Seller extends RealmObject {
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    public int id;

    @Expose
    @SerializedName("effect_date")
    @ColumnInfo(name = "effect_date")
    public String effect_date;

    @Expose
    @SerializedName("agent")
    @ColumnInfo(name = "agent")
    public String agent;

    @Expose
    @SerializedName("phone_number")
    @ColumnInfo(name = "phone_number")
    public String phone_number;

    @Expose
    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    public String first_name;

    @Expose
    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
    public String last_name;

    @Expose
    @SerializedName("ic_passport_numberid")
    @ColumnInfo(name = "ic_passport_number")
    public String ic_passport_number;

    @Expose
    @SerializedName("company_name")
    @ColumnInfo(name = "company_name")
    public String company_name;

    @Expose
    @SerializedName("company_logo")
    @ColumnInfo(name = "company_logo")
    public String company_logo;

    @Expose
    @SerializedName("company_reg_number")
    @ColumnInfo(name = "company_reg_number")
    public String company_reg_number;

    @Expose
    @SerializedName("company_address")
    @ColumnInfo(name = "company_address")
    public String company_address;

    @Expose
    @SerializedName("country_manufacture")
    @ColumnInfo(name = "country_manufacture")
    public String country_manufacture;

    @Expose
    @SerializedName("currency")
    @ColumnInfo(name = "currency")
    public String currency;

    @Expose
    @SerializedName("GST")
    @ColumnInfo(name = "GST")
    public String GST;

    @Expose
    @SerializedName("GST_number")
    @ColumnInfo(name = "GST_number")
    public String GST_number;

    @Expose
    @SerializedName("shop_name")
    @ColumnInfo(name = "shop_name")
    public String shop_name;

    @Expose
    @SerializedName("categories")
    @ColumnInfo(name = "categories")
    public String categories;

    @Expose
    @SerializedName("misc_categories")
    @ColumnInfo(name = "misc_categories")
    public String misc_categories;

    @Expose
    @SerializedName("bank_name")
    @ColumnInfo(name = "bank_name")
    public String bank_name;

    @Expose
    @SerializedName("bank_account_name")
    @ColumnInfo(name = "bank_account_name")
    public String bank_account_name;

    @Expose
    @SerializedName("bank_account_number")
    @ColumnInfo(name = "bank_account_number")
    public String bank_account_number;

    @Expose
    @SerializedName("bank_account_swift")
    @ColumnInfo(name = "bank_account_swift")
    public String bank_account_swift;

    @Expose
    @SerializedName("legal_agreements_id")
    @ColumnInfo(name = "legal_agreements_id")
    public String legal_agreements_id;

    @Expose
    @SerializedName("contact_info_id")
    @ColumnInfo(name = "contact_info_id")
    public String contact_info_id;

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String created_at;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updated_at;

    @Expose
    @SerializedName("avatar")
    @ColumnInfo(name = "avatar")
    public String avatar;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

}
