package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFavorite;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductFavoriteBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductFavoriteEmptyBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class FavoriteAdapter extends SampleAdapter<ProductFavorite, ProductItemNavigator> {

    private ProductItemNavigator mNavigator;
    private FavoriteItemEmptyViewModel mViewModelEmpty;

    public void add(BaseViewModel viewModel, int position) {
        if (mItems != null && mItems.size() >= 0) {
            mItems.add(position, viewModel);
            notifyItemInserted(position);
        }
    }

    public void remove(BaseViewModel viewModel) {
        if (mItems != null && mItems.size() > 0) {
            int position = indexOf(viewModel);
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public int getItemSize() {
        return mItems != null ? mItems.size() : 0;
    }

    protected FavoriteItemEmptyViewModel createEmptyViewModel(ProductItemNavigator navigator) {
        BaseViewModel viewModel = navigator.getMainViewModel();
        FavoriteItemEmptyViewModel viewModelEmpty = new FavoriteItemEmptyViewModel(viewModel.getDataManager(), viewModel.getSchedulerProvider());
        return viewModelEmpty;
    }

    protected FavoriteItemViewModel createViewModel(ProductFavorite favorite, ProductItemNavigator navigator) {
        BaseViewModel viewModel = navigator.getMainViewModel();
        FavoriteItemViewModel itemViewModel = new FavoriteItemViewModel(viewModel.getDataManager(), viewModel.getSchedulerProvider());
        itemViewModel.setNavigator(navigator);
        itemViewModel.inputValue(favorite);
        return itemViewModel;
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

    public int getItemsSize() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public FavoriteItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductFavoriteBinding itemViewBinding = ViewItemProductFavoriteBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteItemViewHolder(itemViewBinding, mNavigator, mItems);
    }

    @Override
    public void addItems(RealmResults<ProductFavorite> results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        mViewModelEmpty = createEmptyViewModel(navigator);
        if (results != null) {
            for (ProductFavorite item : results) {
                FavoriteItemViewModel itemViewModel = createViewModel(item, navigator);
                itemViewModel.variantName.set(item.product_name);
                mItems.add(itemViewModel);
            }
        }
    }

    @Override
    protected void recycle() {
        mItems.clear();
        mItems = null;
        mViewModelEmpty = null;
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

}
