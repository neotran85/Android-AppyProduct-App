package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

public class FavoriteAdapter extends ProductAdapter {

    public void removedFavorite(int position, boolean isFavorite) {
        if (!isFavorite) {
            if (mItems != null && mItems.size() > 0) {
                mItems.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public int getFavoriteCount() {
        return mItems.size();
    }

    @Override
    protected BaseViewHolder getEmptyHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view = inflater.inflate(getEmptyItemLayout(), parent, false);
        return new SampleItemEmptyViewHolder(view);
    }

}
