package com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter;

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

    @Override
    public void onBind(int position) {
        SearchItemViewModel viewModel = (SearchItemViewModel) mAdapter.getItem(position);
        mBinding.setViewModel(viewModel);
        mBinding.setNavigator(viewModel.getNavigator());
    }
}
