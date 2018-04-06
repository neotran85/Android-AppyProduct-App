package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCategoryBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

public class CategoryItemViewHolder extends BaseViewHolder {

    protected ViewItemProductCategoryBinding mBinding;
    protected SampleAdapter mAdapter;

    protected CategoryItemViewHolder(ViewItemProductCategoryBinding binding, SampleAdapter adapter, CategoryItemNavigator navigator) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.setNavigator(navigator);
        mAdapter = adapter;
    }

    public ViewItemProductCategoryBinding getBinding() {
        return mBinding;
    }

    public void setViewModel(CategoryItemViewModel viewModel) {
        if (mBinding != null) {
            mBinding.setViewModel(viewModel);
        }
    }

    @Override
    public void onBind(int position) {
        CategoryItemViewModel viewModel = (CategoryItemViewModel) mAdapter.getItem(position);
        setViewModel(viewModel);
    }
}
