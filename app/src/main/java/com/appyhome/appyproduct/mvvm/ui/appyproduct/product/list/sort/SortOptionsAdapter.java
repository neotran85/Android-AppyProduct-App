package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSortBinding;

public class SortOptionsAdapter extends BaseAdapter {

    Context mContext;
    private SortOption[] mListItem;

    private SortNavigator mNavigator;

    public SortOptionsAdapter() {
    }

    public void setUp(Context context, SortOption[] listItem, SortNavigator navigator) {
        this.mContext = context;
        this.mListItem = listItem;
        this.mNavigator = navigator;
    }

    public int getCount() {
        return mListItem != null ? mListItem.length : 0;
    }

    public Object getItem(int i) {
        return mListItem != null && mListItem.length > 0 ? mListItem[i] : null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View arg1, ViewGroup viewGroup) {
        SortOption data = mListItem[position];
        ViewItemProductSortBinding binding = ViewItemProductSortBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        binding.setViewModel(data);
        View view = binding.getRoot();
        view.setTag(data);
        binding.setNavigator(mNavigator);
        return view;
    }
}
