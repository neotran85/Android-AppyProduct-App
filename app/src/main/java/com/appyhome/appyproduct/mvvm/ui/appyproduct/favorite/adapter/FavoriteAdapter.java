package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductFavoriteEmptyBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;

import io.realm.RealmResults;

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

    public void addItems(ProductItemNavigator navigator, RealmResults<ProductFavorite> results) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = createEmptyViewModel(navigator);
        if (results != null) {
            for (ProductFavorite item : results) {
                ProductItemViewModel itemViewModel = createViewModel(item.toProduct(), navigator, true);
                itemViewModel.variantName.set(item.product_name);
                mItems.add(itemViewModel);
            }
        }
    }

    @Override
    protected BaseViewHolder getEmptyHolder(ViewGroup parent) {
        ViewItemProductFavoriteEmptyBinding itemViewBinding = ViewItemProductFavoriteEmptyBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteItemEmptyViewHolder(itemViewBinding);
    }

    public class FavoriteItemEmptyViewHolder extends BaseViewHolder {
        public FavoriteItemEmptyViewHolder(ViewItemProductFavoriteEmptyBinding binding) {
            super(binding.getRoot());
        }

        @Override
        public void onBind(int position) {

        }
    }

}
