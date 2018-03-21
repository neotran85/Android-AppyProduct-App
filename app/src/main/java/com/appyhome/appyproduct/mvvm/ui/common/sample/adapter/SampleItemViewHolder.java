package com.appyhome.appyproduct.mvvm.ui.common.sample.adapter;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSampleBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

public class SampleItemViewHolder extends BaseViewHolder {

    protected ViewItemProductSampleBinding mBinding;
    protected SampleItemAdapter mAdapter;

    protected SampleItemViewHolder(ViewItemProductSampleBinding binding, SampleItemAdapter adapter) {
        super(binding.getRoot());
        mBinding = binding;
        mAdapter = adapter;
    }

    @Override
    public void onBind(int position) {
        SampleItemViewModel viewModel = (SampleItemViewModel) mAdapter.getItem(position);
        this.mBinding.setViewModel(viewModel);
        this.mBinding.getRoot().setTag(viewModel);
        this.mBinding.setNavigator(viewModel.getNavigator());
    }
}
