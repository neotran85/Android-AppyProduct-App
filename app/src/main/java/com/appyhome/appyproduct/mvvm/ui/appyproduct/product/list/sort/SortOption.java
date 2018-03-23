package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;

import android.databinding.ObservableField;

public enum SortOption {
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
}
