package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import android.view.View;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmResults;

public interface ListProductOrderNavigator {
    void showAlert(String message);

    void close();

    void done_syncAllProductOrders(RealmResults<ProductOrder> orders);

    void onSwitchTab(View view);
}
