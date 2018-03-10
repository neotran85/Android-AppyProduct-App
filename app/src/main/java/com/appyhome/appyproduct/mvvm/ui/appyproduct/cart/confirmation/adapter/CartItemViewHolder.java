package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;


import android.graphics.Paint;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemConfirmationBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

public class CartItemViewHolder extends BaseViewHolder {

    private ViewItemProductCartItemConfirmationBinding mBinding;
    private CartAdapter mAdapter;

    public CartItemViewHolder(ViewItemProductCartItemConfirmationBinding binding, CartAdapter adapter) {
        super(binding.getRoot());
        mBinding = binding;
        mAdapter = adapter;
        mBinding.tvOriginalPrice.setPaintFlags(mBinding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public ViewItemProductCartItemConfirmationBinding getBinding() {
        return mBinding;
    }

    @Override
    public void onBind(int position) {
        CartItemViewModel viewModel = (CartItemViewModel) mAdapter.getItems().get(position);
        if (mBinding != null) {
            mBinding.setViewModel(viewModel);
        }
    }
}
