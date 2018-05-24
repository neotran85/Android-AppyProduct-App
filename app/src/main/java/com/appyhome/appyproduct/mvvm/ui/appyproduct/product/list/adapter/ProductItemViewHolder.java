package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

import java.util.ArrayList;

public class ProductItemViewHolder extends BaseViewHolder {

    private ViewItemProductBinding mBinding;
    private ArrayList<BaseViewModel> mItems;

    public ProductItemViewHolder(ViewItemProductBinding binding, ProductItemNavigator navigator, ArrayList<BaseViewModel> items) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.setNavigator(navigator);
        mItems = items;
    }

    public ViewItemProductBinding getBinding() {
        return mBinding;
    }

    @Override
    public void onBind(int position) {
        ProductItemViewModel viewModel = (ProductItemViewModel) mItems.get(position);
        mBinding.setViewModel(viewModel);
    }
}
