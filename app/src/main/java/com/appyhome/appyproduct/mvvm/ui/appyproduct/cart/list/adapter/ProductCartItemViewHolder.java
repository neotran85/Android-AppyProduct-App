package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;


import android.graphics.Paint;
import android.view.View;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;

public class ProductCartItemViewHolder extends BaseViewHolder {

    private ViewItemProductCartItemBinding mBinding;
    private ProductCartAdapter mAdapter;

    public ProductCartItemViewHolder(ViewItemProductCartItemBinding binding, ProductCartAdapter adapter) {
        super(binding.getRoot());
        mBinding = binding;
        mAdapter = adapter;
        mBinding.tvOriginalPrice.setPaintFlags(mBinding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    public ViewItemProductCartItemBinding getBinding() {
        return mBinding;
    }

    private String getSellerName() {
        return mBinding.getViewModel().sellerName.get();
    }

    private void removeCartItem(ProductCartItemViewModel viewModel) {
        mAdapter.removeCartItem(viewModel);
    }

    @Override
    public void onBind(int position) {
        ProductCartItemViewModel viewModel = (ProductCartItemViewModel) mAdapter.getItems().get(position);
        if (mBinding != null) {
            mBinding.setViewModel(viewModel);
            mBinding.llItemView.setTag(mBinding.getViewModel());
            mBinding.llItemView.setOnClickListener(mAdapter);
            OnClickItemListener handler = new OnClickItemListener(viewModel);
            ViewUtils.setOnClickListener(handler, mBinding.btnDecrease,
                    mBinding.btnIncrease, mBinding.cbWillBuy,
                    mBinding.cbCheckAll, mBinding.tvEdit, mBinding.llRemoveItemCart);
        }
    }

    public class OnClickItemListener implements View.OnClickListener {

        ProductCartItemViewModel viewModel;

        public OnClickItemListener(ProductCartItemViewModel item) {
            viewModel = item;
        }

        public void onClick(View view) {
            int amount = 0;
            switch (view.getId()) {
                case R.id.btnDecrease:
                    amount = Integer.valueOf(viewModel.amount.get());
                    amount = amount - 1;
                    viewModel.amount.set(amount + "");
                    if (amount <= 0) {
                        removeCartItem(viewModel);
                    }
                    mAdapter.updateTotalCost();
                    break;
                case R.id.cbWillBuy:
                    mAdapter.updateCheckAllBySellerName(getSellerName());
                    break;
                case R.id.btnIncrease:
                    amount = Integer.valueOf(viewModel.amount.get()) + 1;
                    viewModel.amount.set(amount + "");
                    mAdapter.updateTotalCost();
                    break;
                case R.id.cbCheckAll:
                    boolean isChecked = mBinding.cbCheckAll.isChecked();
                    mAdapter.pressCheckAllBySellerName(isChecked, getSellerName());
                    break;
                case R.id.tvEdit:
                    boolean current = viewModel.isEditMode.get();
                    mAdapter.updateEditModeBySellerName(!current, getSellerName());
                    break;
                case R.id.llRemoveItemCart:
                    removeCartItem(viewModel);
                    break;

            }
        }
    }
}
