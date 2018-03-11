package com.appyhome.appyproduct.mvvm.ui.common.sample.adapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCategoryBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSampleBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

public class SampleItemViewHolder extends BaseViewHolder {

    protected ViewItemProductSampleBinding mBinding;
    protected SampleItemAdapter mAdapter;

    protected SampleItemViewHolder(ViewItemProductSampleBinding binding, SampleItemAdapter adapter) {
        super(binding.getRoot());
        mBinding = binding;
        mAdapter = adapter;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        if (mBinding != null) {
            mBinding.llItemView.setTag(mBinding.getViewModel());
            mBinding.llItemView.setOnClickListener(listener);
        }
    }

    @Override
    public void onBind(int position) {
        SampleItemViewModel viewModel = (SampleItemViewModel) mAdapter.getItem(position);
        this.mBinding.setViewModel(viewModel);
        this.setOnClickListener(mAdapter);
    }
}
