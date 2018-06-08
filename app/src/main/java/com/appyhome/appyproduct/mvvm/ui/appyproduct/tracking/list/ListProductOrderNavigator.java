package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;

import java.util.ArrayList;

public interface ListProductOrderNavigator {
    void showAlert(String message);
    void showProductOrders(ArrayList<ProductOrder> activeList, ArrayList<ProductOrder> historyList, ArrayList<ProductOrder> canceledList);
}
