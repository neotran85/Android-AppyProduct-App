package com.appyhome.appyproduct.mvvm.ui.common.component.cart;

import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewToolbarBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.SearchActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

public class SearchToolbarViewHolder extends BaseViewHolder {

    private ViewToolbarBinding mBinding;

    private BaseActivity mActivity;

    private SearchToolbarViewModel mViewModel;

    public SearchToolbarViewHolder(BaseActivity activity, ViewGroup parent, boolean isFullMode, boolean isBackShowed, String keyword) {
        super(parent);
        mBinding = ViewToolbarBinding
                .inflate(LayoutInflater.from(activity), parent, true);
        mActivity = activity;
        mBinding.setNavigator(this);
        BaseViewModel baseViewModel = mActivity.getViewModel();
        mViewModel = new SearchToolbarViewModel(baseViewModel.getDataManager(), baseViewModel.getSchedulerProvider());
        mBinding.setViewModel(mViewModel);
        mViewModel.isFullMode.set(isFullMode);
        mViewModel.isBackButtonShowed.set(isBackShowed);
        mViewModel.keywords.set(keyword);
        mViewModel.hasKeywords.set(keyword != null && keyword.length() > 0);
        boolean manyWords = keyword != null && keyword.length() > 32;
        mBinding.tvKeywords.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().
                getDimension(manyWords ? R.dimen.text_size_small : R.dimen.text_size_normal));
    }

    public void openSearchActivity() {
        if (mViewModel.hasKeywords.get()) {
            mActivity.finish();
        } else {
            Intent intent = SearchActivity.getStartIntent(mActivity);
            intent.putExtra("keyword", mBinding.tvKeywords.getText().toString());
            mActivity.startActivity(intent);
        }
    }

    public void back() {
        if (mActivity != null) {
            mActivity.finish();
        }
    }

    public void openProductCart() {
        if (!mActivity.askForLogin("Please login to view your cart.")) {
            if (mBinding.getViewModel().totalItemsCount.get() > 0) {
                Intent intent = ProductCartListActivity.getStartIntent(mActivity);
                mActivity.startActivity(intent);
            } else {
                AlertManager.getInstance(mActivity).showLongToast(mActivity.getString(R.string.toast_your_cart_is_empty));
            }
        }
    }

    @Override
    public void onBind(int position) {
        mBinding.getViewModel().updateTotalCountProductCart();
    }
}
