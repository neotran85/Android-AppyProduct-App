package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCategorySubBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

public class SubCategoryItemViewHolder extends BaseViewHolder {

    protected ViewItemProductCategorySubBinding mBinding;
    protected SampleAdapter mAdapter;

    protected SubCategoryItemViewHolder(ViewItemProductCategorySubBinding binding, SampleAdapter adapter) {
        super(binding.getRoot());
        mBinding = binding;
        mAdapter = adapter;
    }

    public ViewItemProductCategorySubBinding getBinding() {
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
        mBinding.getRoot().setTag(viewModel);
        mBinding.setNavigator(viewModel.getNavigator());
    }
}
