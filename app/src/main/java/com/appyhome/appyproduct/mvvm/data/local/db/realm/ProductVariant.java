package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import android.arch.persistence.room.ColumnInfo;
import android.text.TextUtils;

import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductVariant extends RealmObject {
    @Expose
    @SerializedName("model_id")
    @ColumnInfo(name = "model_id")
    @PrimaryKey
    public String model_id;

    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    public int id;

    @Expose
    @SerializedName("product_id")
    @ColumnInfo(name = "product_id")
    public int product_id;

    @Expose
    @SerializedName("product_name")
    @ColumnInfo(name = "product_name")
    public String product_name;

    @Expose
    @SerializedName("variant_type")
    @ColumnInfo(name = "variant_type")
    public String variant_type;

    @Expose
    @SerializedName("variant_name")
    @ColumnInfo(name = "variant_name")
    public String variant_name;

    @Expose
    @SerializedName("sku")
    @ColumnInfo(name = "sku")
    public String sku;

    @Expose
    @SerializedName("quantity")
    @ColumnInfo(name = "quantity")
    public int quantity;

    @Expose
    @SerializedName("price")
    @ColumnInfo(name = "price")
    public double price;

    @Expose
    @SerializedName("stroke_price")
    @ColumnInfo(name = "stroke_price")
    public float stroke_price;

    @Expose
    @SerializedName("loyalty_id")
    @ColumnInfo(name = "loyalty_id")
    public int loyalty_id;

    @Expose
    @SerializedName("flag")
    @ColumnInfo(name = "flag")
    public String flag;

    @Expose
    @SerializedName("date_available")
    @ColumnInfo(name = "date_available")
    public String date_available;

    @Expose
    @SerializedName("date_unavailable")
    @ColumnInfo(name = "date_unavailable")
    public String date_unavailable;

    @Expose
    @SerializedName("weight")
    @ColumnInfo(name = "weight")
    public float weight;

    @Expose
    @SerializedName("length")
    @ColumnInfo(name = "length")
    public float length;

    @Expose
    @SerializedName("width")
    @ColumnInfo(name = "width")
    public float width;

    @Expose
    @SerializedName("height")
    @ColumnInfo(name = "height")
    public float height;

    @Expose
    @SerializedName("seo_keywords")
    @ColumnInfo(name = "seo_keywords")
    public String seo_keywords;

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    public String description;

    @Expose
    @SerializedName("stock_status")
    @ColumnInfo(name = "stock_status")
    public String stock_status;

    @Expose
    @SerializedName("set_id")
    @ColumnInfo(name = "set_id")
    public int set_id;

    @Expose
    @SerializedName("sort_order")
    @ColumnInfo(name = "sort_order")
    public int sort_order;


    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String created_at;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updated_at;

    @Expose
    @SerializedName("images")
    public RealmList<ProductVariantImage> images;

    @Expose
    @SerializedName("avatar")
    public String avatar;

    @Expose
    @SerializedName("country_manu")
    @ColumnInfo(name = "country_manu")
    public String country_manu;

    @Expose
    @SerializedName("stock_location")
    @ColumnInfo(name = "stock_location")
    public String stock_location;

    @Expose
    @SerializedName("seller_name")
    @ColumnInfo(name = "seller_name")
    public String seller_name;

    @Expose
    @SerializedName("seller_id")
    @ColumnInfo(name = "seller_id")
    public long seller_id;

    @Expose
    @SerializedName("more_info")
    @ColumnInfo(name = "more_info")
    public String more_info;

    public String getManufacturer() {
        return country_manu != null && country_manu.equals("MY")
                ? "Local" : "Overseas";
    }

    public boolean isLocal() {
        return stock_location != null && stock_location.equals("MY");
    }

    public String getShippingFrom() {
        return stock_location != null && stock_location.equals("MY")
                ? "Domestic / Local" : "International / Overseas";
    }

    public boolean isAvailable() {
        return stock_status != null && stock_status.equals("AVB");
    }

    public String getAvailable() {
        return isAvailable() ? "Available" : "Unavailable";
    }

    public String getSize() {
        if (width > 0)
            return "L" + DataUtils.roundNumber(length, 2) + " x "
                    + "W" + DataUtils.roundNumber(width, 2) + " x " + "H" + DataUtils.roundNumber(height, 2);
        return "";
    }

    public String getDescription() {
        if (description != null && description.length() > 0) {
            String text = description.replace("\n\n", "\n");
            return text;
        }
        return "";
    }

    private boolean isContainsSimilarity(String text, String key1, String key2) {
        if (text != null && key1 != null && text.length() > 0
                && key1.length() > 0 && key2 != null && key2.length() > 0) {
            String textStr = text.toLowerCase();
            return textStr.contains(key1) && textStr.contains(key2);
        }
        return false;
    }

    public HashMap<String, String> parseDescription() {
        if (description != null && description.length() > 0) {
            String text = description.replace("\n\n", "\n");
            String[] array = text.split("\n");
            if (array != null && array.length > 0) {
                HashMap<String, String> map = new HashMap<>();
                for (String str : array) {
                    if (str.contains(":")) {
                        String[] result = str.split(":");
                        if (result != null && result.length == 2) {
                            String title = DataUtils.getStringNotNull(result[0].trim());
                            String content = DataUtils.getStringNotNull(result[1].trim());
                            if (title.length() > 0 && content.length() > 0)
                                map.put(result[0].trim(), result[1].trim());
                        }
                    }
                }
                return map;
            }
        }
        return null;
    }

    public String getWarranty() {
        if (description != null && description.length() > 0) {
            String text = description.replace("\n\n", "\n");
            String[] array = text.split("\n");
            if (array != null && array.length > 0) {
                ArrayList<String> result = new ArrayList<>();
                for (String str : array) {
                    if (isContainsSimilarity(str, "warranty", " ")) {
                        result.add(str);
                    }
                }
                if (result.size() > 0) {
                    return TextUtils.join("\n", result);
                }
            }
        }
        return "";
    }

    public String getDeliveryEstimation() {
        if (isLocal())
            return "1-7 Business Days";
        else if (description != null && description.length() > 0) {
            // OVERSEAS
            String text = description.replace("\n\n", "\n");
            String[] array = text.split("\n");
            if (array != null && array.length > 0) {
                for (String str : array) {
                    if (isContainsSimilarity(str, "delivery time", "days")) {
                        String[] result = str.split("Delivery time is ");
                        if (result != null && result.length == 2) {
                            return result[1];
                        } else return str;
                    }
                }
            }
        }
        return "";
    }

    public String getPriceNote() {
        if (description != null && description.length() > 0) {
            String text = description.replace("\n\n", "\n");
            String[] array = text.split("\n");
            if (array != null && array.length > 0) {
                if (isLocal()) {
                    ArrayList<String> result = new ArrayList<>();
                    for (String str : array) {
                        if (isContainsSimilarity(str, "price", " ")) {
                            result.add(str);
                        }
                    }
                    return result.size() > 0 ? TextUtils.join("\n", result) : "";
                } else {
                    for (String str : array) {
                        if (isContainsSimilarity(str, "from overseas", "inclusive of")) {
                            return str;
                        }
                    }
                }
            }
        }
        return "";
    }
}
