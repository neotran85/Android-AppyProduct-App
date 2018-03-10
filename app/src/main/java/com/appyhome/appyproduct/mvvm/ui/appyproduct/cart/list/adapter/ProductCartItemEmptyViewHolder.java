package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;


import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartEmptyBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

public class ProductCartItemEmptyViewHolder extends BaseViewHolder {

    private ViewItemProductCartEmptyBinding mBinding;
    private ProductCartAdapter mAdapter;

    public ProductCartItemEmptyViewHolder(ViewItemProductCartEmptyBinding binding, ProductCartItemNavigator navigator) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.setNavigator(navigator);
    }

    public ViewItemProductCartEmptyBinding getBinding() {
        return mBinding;
    }

    @Override
    public void onBind(int position) {

    }
}
