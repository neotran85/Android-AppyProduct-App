package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductBought;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

public class OrderItemViewModel extends BaseViewModel<ListProductOrderNavigator> {
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> date = new ObservableField<>("");
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> quantity = new ObservableField<>("");

    public OrderItemViewModel(ListProductOrderNavigator navigator, ProductOrder order) {
        setNavigator(navigator);
        if (order != null) {
            long quantityTotal = 0;
            String titleStr = "";
            if (order.items != null && order.items.size() > 0) {
                titleStr = order.items.get(0) != null ? order.items.get(0).title : "";
                for (ProductBought item : order.items) {
                    quantityTotal = quantityTotal + item.quantity;
                }
            }
            title.set("Order ID: " + order.id);
            date.set("Date created: " + order.created_at);
            name.set(titleStr);
            quantity.set("Quantity: " + quantityTotal);
        }
    }
}
