package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;

public interface ProductTrackingNavigator {
    void showAlert(String message);

    void showOrder(ProductOrder order);
}
