package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;


import android.graphics.Paint;
import android.view.View;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartEmptyBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

public class ProductCartItemEmptyViewHolder extends BaseViewHolder {

    private ViewItemProductCartEmptyBinding mBinding;
    private ProductCartAdapter mAdapter;

    public ViewItemProductCartEmptyBinding getBinding() {
        return mBinding;
    }

    public ProductCartItemEmptyViewHolder(ViewItemProductCartEmptyBinding binding, ProductCartItemNavigator navigator) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.setNavigator(navigator);
    }

    @Override
    public void onBind(int position) {

    }
}
