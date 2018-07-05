package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductOrderBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ListProductOrderAdapter extends SampleAdapter<ProductOrder, ListProductOrderNavigator> {

    @Override
    public BaseViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductOrderBinding itemViewBinding = ViewItemProductOrderBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderItemViewHolder(itemViewBinding);
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
        return R.layout.view_item_product_order_empty;
    }

    public class OrderItemViewHolder extends BaseViewHolder {

        private ViewItemProductOrderBinding mBinding;

        public OrderItemViewHolder(ViewItemProductOrderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public ViewItemProductOrderBinding getBinding() {
            return mBinding;
        }

        @Override
        public void onBind(int position) {
            OrderItemViewModel viewModel = (OrderItemViewModel) getItem(position);
            mBinding.setViewModel(viewModel);
        }
    }
}