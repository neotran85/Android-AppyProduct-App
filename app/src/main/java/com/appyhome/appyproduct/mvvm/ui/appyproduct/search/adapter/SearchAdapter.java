package com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchItemBinding;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class SearchAdapter extends SampleAdapter<SearchItem, SearchItemNavigator> {

    private SearchItemNavigator mNavigator;

    public SearchAdapter() {
        this.mItems = null;
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_product_search_empty;
    }

    private SearchItemViewModel createViewModel(SearchItem item, SearchItemNavigator navigator) {
        return new SearchItemViewModel(item.content, navigator);
    }

    public void addItems(RealmResults<SearchItem> results, SearchItemNavigator navigator) {
        mNavigator = navigator;
        mItems = new ArrayList<>();
        if (results != null) {
            for (SearchItem item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    @Override
    protected void recycle() {
        if (mItems != null) {
            mItems.clear();
            mItems = null;
        }
    }

    @Override
    protected SearchItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductSearchItemBinding itemViewBinding = ViewItemProductSearchItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchItemViewHolder(itemViewBinding, this, mNavigator);
    }
}