package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;

import android.databinding.ObservableField;

public enum SortOption {
    PRICE_HIGHEST(0, "Price High To Low"),
    PRICE_LOWEST(1, "Price Low To High"),
    LATEST(2, "Latest Arrival"),
    PROMOTION(3, "Promotion"),
    RATING(4, "Rating"),
    POPULAR(5, "Popular");

    private int value;
    private String name;
    public ObservableField <Boolean> checked = new ObservableField<>(false);

    SortOption(int v, String n) {
        value = v;
        name = n;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
