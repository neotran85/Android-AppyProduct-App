package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;


import android.graphics.Paint;
import android.view.View;
import android.widget.CompoundButton;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;

public class ProductCartItemViewHolder extends BaseViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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
        mBinding.cbWillBuy.setOnCheckedChangeListener(this);
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
                if (amount > 0) {
                    amount = amount - 1;
                } else amount = 0;
                viewModel.amount.set(amount + "");
                mAdapter.updateTotalCost();
                break;
            case R.id.btnIncrease:
                amount = Integer.valueOf(mBinding.getViewModel().amount.get()) + 1;
                viewModel.amount.set(amount + "");
                mAdapter.updateTotalCost();
                break;
            case R.id.cbCheckAll:
                boolean isChecked = mBinding.cbCheckAll.isChecked();
                pressCheckAll(isChecked);
                break;
            case R.id.tvEdit:
                boolean current = mBinding.getViewModel().isEditMode.get();
                updateEditMode(!current);
                break;
            case R.id.llRemoveItemCart:
                removeCartItem();
                break;

        }
        mAdapter.isChangedByUser = true;
    }

    private void removeCartItem() {
        if (checkIfItemsNotEmpty()) {
            String sellerName = mBinding.getViewModel().sellerName.get();
            ArrayList<ProductCartItemViewModel> array = mAdapter.viewModelManager.get(sellerName);
            boolean isFirstItemOfStore = mBinding.getViewModel().isFirstProductOfStore.get();
            mBinding.getViewModel().removeProductCartItem(mBinding.getViewModel().getProductCartId());
            int pos = mAdapter.getItems().indexOf(mBinding.getViewModel());
            mAdapter.getItems().remove(mBinding.getViewModel());
            mAdapter.notifyItemRemoved(pos);

            // UPDATE IF FIRST ITEM OF STORE IS REMOVED
            if (array != null && array.size() > 0) {
                array.remove(mBinding.getViewModel());
                if (isFirstItemOfStore && array.size() > 0) {
                    ProductCartItemViewModel firstItem = array.get(0);
                    firstItem.isFirstProductOfStore.set(true);
                    updateCheckAll(firstItem);
                }
            }
            mAdapter.updateTotalCost();
        }
    }

    private boolean checkIfItemsNotEmpty() {
        return mAdapter != null && mAdapter.getItems() != null && mAdapter.getItems().size() > 0;
    }

    private void updateEditMode(boolean isEditable) {
        ProductCartItemViewModel viewModel = mBinding.getViewModel();
        ArrayList<ProductCartItemViewModel> array = mAdapter.viewModelManager.get(viewModel.sellerName.get());
        if (array != null && array.size() > 0) {
            for (ProductCartItemViewModel item : array) {
                item.isEditMode.set(isEditable);
            }
        }
    }

    private void updateCheckAll(ProductCartItemViewModel viewModel) {
        ArrayList<ProductCartItemViewModel> array = mAdapter.viewModelManager.get(viewModel.sellerName.get());
        if (array != null && array.size() > 0) {
            boolean result = true;
            for (ProductCartItemViewModel item : array) {
                if (!item.checked.get()) {
                    result = false;
                    break;
                }
            }
            ProductCartItemViewModel firstItem = array.get(0);
            firstItem.checkedAll.set(result);
        }
        mAdapter.updateTotalCost();
    }

    private void pressCheckAll(boolean isChecked) {
        ProductCartItemViewModel viewModel = mBinding.getViewModel();
        ArrayList<ProductCartItemViewModel> array = mAdapter.viewModelManager.get(viewModel.sellerName.get());
        if (array != null && array.size() > 0) {
            for (ProductCartItemViewModel item : array) {
                item.checked.set(isChecked);
            }
            array.get(0).checkedAll.set(isChecked);
        }
        mAdapter.updateTotalCost();
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mAdapter.isChangedByUser = true;
        mBinding.getViewModel().checked.set(isChecked);
        updateCheckAll(mBinding.getViewModel());
    }
}
