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
    }

    public ViewItemProductCartItemConfirmationBinding getBinding() {
        return mBinding;
    }

    @Override
    public void onBind(int position) {
        CartItemViewModel viewModel = (CartItemViewModel) mAdapter.getItems().get(position);
        mBinding.setViewModel(viewModel);
        viewModel.updateShippingCost();
    }
}
