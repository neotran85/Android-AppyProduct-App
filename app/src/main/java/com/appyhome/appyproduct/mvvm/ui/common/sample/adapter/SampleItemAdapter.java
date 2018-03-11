package com.appyhome.appyproduct.mvvm.ui.common.sample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Payment;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemConfirmationBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSampleBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.ConfirmationNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter.CartItemViewHolder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter.CartItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmObject;
import io.realm.RealmResults;

public class SampleItemAdapter extends SampleAdapter<RealmObject, SampleItemNavigator> {

    public SampleItemAdapter() {
        this.mItems = null;
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_product_cart_empty;
    }

    public void addItems(RealmResults<RealmObject> results, SampleItemNavigator navigator) {}

    @Override
    protected void recycle() {
        if(mItems != null) {
            mItems.clear();
            mItems = null;
        }
    }


    @Override
    public void onClick(View view) {
        // DO NOTHING
    }

    @Override
    protected SampleItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductSampleBinding itemViewBinding = ViewItemProductSampleBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SampleItemViewHolder(itemViewBinding, this);
    }
}