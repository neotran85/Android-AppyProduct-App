package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductFavoriteBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

import java.util.ArrayList;

public class FavoriteItemViewHolder extends BaseViewHolder {
    private ViewItemProductFavoriteBinding mBinding;
    private ArrayList<BaseViewModel> mItems;

    public FavoriteItemViewHolder(ViewItemProductFavoriteBinding binding, ProductItemNavigator navigator, ArrayList<BaseViewModel> items) {
        super(binding.getRoot());
        mBinding = binding;
        mItems = items;
        mBinding.setNavigator(navigator);
    }

    public ViewItemProductFavoriteBinding getBinding() {
        return mBinding;
    }

    @Override
    public void onBind(int position) {
        FavoriteItemViewModel viewModel = (FavoriteItemViewModel) mItems.get(position);
        mBinding.setViewModel(viewModel);
    }
}
