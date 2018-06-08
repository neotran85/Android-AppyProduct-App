package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ListProductOrderAdapter extends SampleAdapter<ProductOrder, ListProductOrderNavigator> {

    @Override
    public BaseViewHolder getContentHolder(ViewGroup parent) {
        return null;
    }

    public void addItem(ProductOrder order, ListProductOrderNavigator navigator) {
        OrderItemViewModel viewModel = new OrderItemViewModel(navigator, order);
        mItems.add(viewModel);
    }

    @Override
    protected void addItems(RealmResults<ProductOrder> items, ListProductOrderNavigator navigator) {
        mItems = new ArrayList<>();
        for (ProductOrder order : items) {
            OrderItemViewModel viewModel = new OrderItemViewModel(navigator, order);
            mItems.add(viewModel);
        }
    }

    @Override
    protected void recycle() {

    }

    @Override
    protected int getEmptyItemLayout() {
        return 0;
    }

}