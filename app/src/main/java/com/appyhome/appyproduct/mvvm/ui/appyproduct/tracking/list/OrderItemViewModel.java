package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

public class OrderItemViewModel extends BaseViewModel<ListProductOrderNavigator> {
    public OrderItemViewModel(ListProductOrderNavigator navigator, ProductOrder order) {
        setNavigator(navigator);
    }
}
