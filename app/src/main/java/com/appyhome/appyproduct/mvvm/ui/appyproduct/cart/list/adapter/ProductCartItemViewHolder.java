package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;

public class ProductCartItemViewHolder extends BaseViewHolder {

    private ViewItemProductCartItemBinding mBinding;
    private ProductCartAdapter mAdapter;
    private Runnable mUpdateRunnable;
    private boolean enableToStartRunnable = true;
    private Handler mHandler;

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
        if (mHandler != null) {
            mHandler.removeCallbacks(mUpdateRunnable);
            mHandler = null;
        }
        if (mAdapter != null)
            mAdapter.removeCartItem(viewModel, true);
    }

    public void setViewModel(ProductCartItemViewModel viewModel) {
        mBinding.setViewModel(viewModel);
        OnClickItemListener handler = new OnClickItemListener(viewModel);
        ViewUtils.setOnClickListener(handler, mBinding.btnDecrease,
                mBinding.btnIncrease, mBinding.cbWillBuy,
                mBinding.cbCheckAll, mBinding.tvEdit, mBinding.llRemoveItemCart);
    }

    private String getString(int idString) {
        if (mBinding != null) {
            View root = mBinding.getRoot();
            if (root != null) {
                Context context = root.getContext();
                if (context != null) {
                    return context.getString(idString);
                }
            }
        }
        return "";
    }

    @Override
    public void onBind(int position) {
        if (mAdapter != null) {
            ProductCartItemViewModel itemViewModel = (ProductCartItemViewModel) mAdapter.getItems().get(position);
            setViewModel(itemViewModel);
            mUpdateRunnable = () -> {
                enableToStartRunnable = true;
                if (itemViewModel != null)
                    itemViewModel.updateProductCartItemAfterPlusAndMinus();
                if (mHandler != null) {
                    mHandler.removeCallbacks(mUpdateRunnable);
                    mHandler = null;
                }
            };
        }
    }

    private void timingForUpdate() {
        if (enableToStartRunnable) {
            enableToStartRunnable = false;
            if (mHandler != null) {
                mHandler.removeCallbacks(mUpdateRunnable);
                mHandler = null;
            }
            mHandler = new Handler();
            mHandler.postDelayed(mUpdateRunnable, 1000);
        }
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
                    } else {
                        timingForUpdate();
                    }
                    break;
                case R.id.cbWillBuy:
                    if (mAdapter != null) {
                        mAdapter.updateCheckAllBySellerName(getSellerName());
                    }
                    viewModel.updateProductCartItemChecked();
                    break;
                case R.id.btnIncrease:
                    amount = Integer.valueOf(viewModel.amount.get()) + 1;
                    if (amount <= viewModel.getVariantStockNumber()) {
                        viewModel.amount.set(amount + "");
                        timingForUpdate();
                        if (mAdapter != null) {
                            mAdapter.updateTotalCost();
                        }
                    } else {
                        if (mBinding.getNavigator() != null)
                            mBinding.getNavigator().showAlert(getString(R.string.unable_to_add_than) + viewModel.getVariantStockNumber());
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
