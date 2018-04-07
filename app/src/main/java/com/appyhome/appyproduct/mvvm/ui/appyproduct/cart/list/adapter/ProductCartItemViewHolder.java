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

    public ProductCartItemViewHolder(ViewItemProductCartItemBinding binding, ProductCartAdapter adapter, ProductCartItemNavigator navigator) {
        super(binding.getRoot());
        mBinding = binding;
        mAdapter = adapter;
        mBinding.setNavigator(navigator);
        mBinding.tvOriginalPrice.setPaintFlags(mBinding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public ViewItemProductCartItemBinding getBinding() {
        return mBinding;
    }

    private String getSellerName() {
        return mBinding.getViewModel().sellerName.get();
    }

    private void removeCartItem(ProductCartItemViewModel viewModel) {
        if (mAdapter != null)
            mAdapter.removeCartItem(viewModel);
    }

    public void setViewModel(ProductCartItemViewModel viewModel) {
        mBinding.setViewModel(viewModel);
        OnClickItemListener handler = new OnClickItemListener(viewModel);
        ViewUtils.setOnClickListener(handler, mBinding.btnDecrease,
                mBinding.btnIncrease, mBinding.cbWillBuy,
                mBinding.cbCheckAll, mBinding.tvEdit, mBinding.llRemoveItemCart);
    }

    @Override
    public void onBind(int position) {
        if (mAdapter != null)
            setViewModel((ProductCartItemViewModel) mAdapter.getItems().get(position));
    }

    public class OnClickItemListener implements View.OnClickListener {

        ProductCartItemViewModel viewModel;

        public OnClickItemListener(ProductCartItemViewModel item) {
            viewModel = item;
        }

        public void onClick(View view) {
            int amount = 0;
            viewModel.alertText.set("");
            switch (view.getId()) {
                case R.id.btnDecrease:
                    amount = Integer.valueOf(viewModel.amount.get());
                    amount = amount - 1;
                    viewModel.amount.set((amount > 1 ? amount : (mAdapter != null ? amount : 1)) + "");
                    if (mAdapter != null)
                        mAdapter.updateTotalCost();
                    if (amount <= 0) {
                        removeCartItem(viewModel);
                    }
                    break;
                case R.id.cbWillBuy:
                    if (mAdapter != null) mAdapter.updateCheckAllBySellerName(getSellerName());
                    break;
                case R.id.btnIncrease:
                    amount = Integer.valueOf(viewModel.amount.get()) + 1;
                    if (amount <= viewModel.getVariantStockNumber()) {
                        viewModel.amount.set(amount + "");
                        if (mAdapter != null)
                            mAdapter.updateTotalCost();
                    } else {
                        mBinding.getNavigator().showAlert("Sorry, unable to add more than " + viewModel.getVariantStockNumber());
                    }
                    break;
                case R.id.cbCheckAll:
                    boolean isChecked = mBinding.cbCheckAll.isChecked();
                    if (mAdapter != null)
                        mAdapter.pressCheckAllBySellerName(isChecked, getSellerName());
                    break;
                case R.id.tvEdit:
                    boolean current = viewModel.isEditMode.get();
                    if (mAdapter != null)
                        mAdapter.updateEditModeBySellerName(!current, getSellerName());
                    break;
                case R.id.llRemoveItemCart:
                    removeCartItem(viewModel);
                    break;

            }
        }
    }
}
