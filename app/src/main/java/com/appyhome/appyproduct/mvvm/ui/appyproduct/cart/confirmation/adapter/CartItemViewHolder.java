package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.confirmation.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemConfirmationBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

public class CartItemViewHolder extends BaseViewHolder {

    private ViewItemProductCartItemConfirmationBinding mBinding;
    private CartAdapter mAdapter;
    private Context mContext;

    public CartItemViewHolder(ViewItemProductCartItemConfirmationBinding binding, CartAdapter adapter) {
        super(binding.getRoot());
        mContext = binding.getRoot().getContext();
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
        mBinding.btnShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel != null && mContext != null) {
                    int count = viewModel.shippingFees != null ?
                            viewModel.shippingFees.keySet().size() : 0;
                    if (count > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        CharSequence[] labels = new CharSequence[count];
                        String[] types = new String[count];
                        int i = 0;
                        int checkedItem = 0;
                        for (String label : viewModel.shippingFees.keySet()) {
                            labels[i] = viewModel.getShippingCostByMethod(label);
                            types[i] = label;
                            if (label.equals(viewModel.getShippingType())) {
                                checkedItem = i;
                            }
                            i++;
                        }
                        builder.setTitle("Shipping via");
                        builder.setSingleChoiceItems(labels, checkedItem, (dialog, item) -> {
                            viewModel.updateShippingCost(types[item]);
                            dialog.dismiss();
                            if (mAdapter != null) mAdapter.updateTotalShippingCost();
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            }
        });
    }
}
