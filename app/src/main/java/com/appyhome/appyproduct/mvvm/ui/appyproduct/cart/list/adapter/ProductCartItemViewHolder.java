package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;


import android.graphics.Paint;
import android.view.View;
import android.widget.CompoundButton;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;

public class ProductCartItemViewHolder extends BaseViewHolder implements View.OnClickListener {

    private ViewItemProductCartItemBinding mBinding;
    private ProductCartAdapter mAdapter;

    public ViewItemProductCartItemBinding getBinding() {
        return mBinding;
    }

    public ProductCartItemViewHolder(ViewItemProductCartItemBinding binding, ProductCartAdapter adapter) {
        super(binding.getRoot());
        mBinding = binding;
        mAdapter = adapter;
        mBinding.tvOriginalPrice.setPaintFlags(mBinding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        mBinding.btnDecrease.setOnClickListener(this);
        mBinding.btnIncrease.setOnClickListener(this);
        mBinding.cbWillBuy.setOnClickListener(this);
        mBinding.cbCheckAll.setOnClickListener(this);
        mBinding.tvEdit.setOnClickListener(this);
        mBinding.llRemoveItemCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int amount = 0;
        ProductCartItemViewModel viewModel = mBinding.getViewModel();
        switch (view.getId()) {
            case R.id.btnDecrease:
                amount = Integer.valueOf(mBinding.getViewModel().amount.get());
                if (amount > 1) {
                    amount = amount - 1;
                } else {
                    mAdapter.removeCartItem(viewModel);
                    return;
                }
                viewModel.amount.set(amount + "");
                mAdapter.updateTotalCost();
                break;
            case R.id.cbWillBuy:
                mAdapter.updateCheckAllBySellerName(getSellerName());
                break;
            case R.id.btnIncrease:
                amount = Integer.valueOf(mBinding.getViewModel().amount.get()) + 1;
                viewModel.amount.set(amount + "");
                mAdapter.updateTotalCost();
                break;
            case R.id.cbCheckAll:
                boolean isChecked = mBinding.cbCheckAll.isChecked();
                mAdapter.pressCheckAllBySellerName(isChecked, getSellerName());
                break;
            case R.id.tvEdit:
                boolean current = mBinding.getViewModel().isEditMode.get();
                mAdapter.updateEditModeBySellerName(!current, getSellerName());
                break;
            case R.id.llRemoveItemCart:
                removeCartItem();
                break;

        }
    }

    private String getSellerName() {
        return mBinding.getViewModel().sellerName.get();
    }

    private void removeCartItem() {
        mAdapter.removeCartItem(mBinding.getViewModel());
    }

    @Override
    public void onBind(int position) {
        ProductCartItemViewModel viewModel = (ProductCartItemViewModel) mAdapter.getItems().get(position);
        if (mBinding != null) {
            mBinding.setViewModel(viewModel);
            mBinding.llItemView.setTag(mBinding.getViewModel());
            mBinding.llItemView.setOnClickListener(mAdapter);
        }
    }
}
