package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;

public interface FilterNavigator {
        void applyFilter();
        void updateUIFilter(ProductFilter filter);
}
