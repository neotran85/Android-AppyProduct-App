package com.appyhome.appyproduct.mvvm.ui.common.component.cart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewToolbarBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

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
        if (mBinding.getViewModel().totalItemsCount.get() > 0) {
            Intent intent = ProductCartListActivity.getStartIntent(mActivity);
            mActivity.startActivity(intent);
        } else {
            AlertManager.getInstance(mActivity).showLongToast(mActivity.getString(R.string.toast_your_cart_is_empty));
        }
    }

    @Override
    public void onBind(int position) {
        mBinding.getViewModel().updateTotalCountProductCart();
    }
}
