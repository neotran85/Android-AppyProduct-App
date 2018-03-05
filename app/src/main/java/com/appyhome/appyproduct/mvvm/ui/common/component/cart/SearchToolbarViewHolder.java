package com.appyhome.appyproduct.mvvm.ui.common.component.cart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.databinding.ViewToolbarBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;

public class SearchToolbarViewHolder extends BaseViewHolder {

    private ViewToolbarBinding mBinding;

    private BaseActivity mActivity;

    public SearchToolbarViewHolder(BaseActivity activity, ViewGroup parent) {
        super(parent);
        mBinding = ViewToolbarBinding
                .inflate(LayoutInflater.from(activity), parent, true);
        mActivity = activity;
        mBinding.setNavigator(this);
        BaseViewModel baseViewModel = mActivity.getViewModel();
        SearchToolbarViewModel viewModel = new SearchToolbarViewModel(baseViewModel.getDataManager(), baseViewModel.getSchedulerProvider());
        mBinding.setViewModel(viewModel);

    }

    public void openProductCart() {
        Intent intent = ProductCartListActivity.getStartIntent(mActivity);
        mActivity.startActivity(intent);
    }

    @Override
    public void onBind(int position) {
        mBinding.getViewModel().updateTotalCountProductCart();
    }
}
