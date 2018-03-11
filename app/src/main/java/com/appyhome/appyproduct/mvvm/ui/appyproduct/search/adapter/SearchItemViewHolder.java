package com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter;

import android.view.View;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSampleBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchItemBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

public class SearchItemViewHolder extends BaseViewHolder {

    protected ViewItemProductSearchItemBinding mBinding;
    protected SearchAdapter mAdapter;

    protected SearchItemViewHolder(ViewItemProductSearchItemBinding binding, SearchAdapter adapter) {
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
        SearchItemViewModel viewModel = (SearchItemViewModel) mAdapter.getItem(position);
        this.mBinding.setViewModel(viewModel);
        this.mBinding.setNavigator(viewModel.getNavigator());
    }
}
