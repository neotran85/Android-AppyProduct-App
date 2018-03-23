package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;

import android.databinding.ObservableField;

import org.json.JSONException;
import org.json.JSONObject;

public enum SortOption {
    UNKNOWN("", "Sort"),
    PRICE_HIGHEST("PRICEHTL", "Price High To Low"),
    PRICE_LOWEST("PRICELTH", "Price Low To High"),
    LATEST("LATEST", "Latest Arrival"),
    RATING("RATING", "Rating"),
    POPULAR("POPULAR", "The Most Popular");

    public ObservableField<Boolean> checked = new ObservableField<>(false);
    private String value;
    private String name;

    SortOption(String v, String n) {
        value = v;
        name = n;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("value", value);
            object.put("name", name);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void fromJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            value = object.getString("value");
            name = object.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
