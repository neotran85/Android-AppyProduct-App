package com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Sample;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCategoryBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

public class CategoryItemViewHolder extends BaseViewHolder {

    protected ViewItemProductCategoryBinding mBinding;
    protected SampleAdapter mAdapter;

    protected CategoryItemViewHolder(ViewItemProductCategoryBinding binding, SampleAdapter adapter) {
        super(binding.getRoot());
        mBinding = binding;
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
        mBinding.getRoot().setTag(viewModel);
        mBinding.setNavigator(viewModel.getNavigator());
    }
}
