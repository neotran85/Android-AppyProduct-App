package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.imageadapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductGalleryBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchItemBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

public class GalleryItemViewHolder extends BaseViewHolder {

    protected ViewItemProductGalleryBinding mBinding;
    protected GalleryAdapter mAdapter;

    protected GalleryItemViewHolder(ViewItemProductGalleryBinding binding, GalleryAdapter adapter) {
        super(binding.getRoot());
        mBinding = binding;
        mAdapter = adapter;
    }

    @Override
    public void onBind(int position) {
        GalleryItemViewModel viewModel = (GalleryItemViewModel) mAdapter.getItem(position);
        this.mBinding.setViewModel(viewModel);
        this.mBinding.getRoot().setTag(viewModel);
        this.mBinding.setNavigator(viewModel.getNavigator());
    }
}
