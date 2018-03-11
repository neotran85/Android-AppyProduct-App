package com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSampleBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchItemBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.RealmResults;

public class SearchAdapter extends SampleAdapter<SearchItem, SearchItemNavigator> {

    public SearchAdapter() {
        this.mItems = null;
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_product_cart_empty;
    }

    private SearchItemViewModel createViewModel(SearchItem product, SearchItemNavigator navigator) {
        return new SearchItemViewModel(navigator);
    }

    public void addItems(RealmResults<SearchItem> results, SearchItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (SearchItem item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

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
    protected SearchItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductSearchItemBinding itemViewBinding = ViewItemProductSearchItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchItemViewHolder(itemViewBinding, this);
    }
}