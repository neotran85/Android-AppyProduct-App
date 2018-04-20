package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCached;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartEmptyBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductFavoriteEmptyBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemEmptyViewHolder;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter.ProductCartItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.FavoriteNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

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
                ProductItemViewModel  itemViewModel = createViewModel(item.toProduct(), navigator, true);
                itemViewModel.variantName.set(item.variant_name);
                itemViewModel.setVariantId(item.variant_id);
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
