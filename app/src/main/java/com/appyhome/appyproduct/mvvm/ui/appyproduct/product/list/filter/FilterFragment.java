package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter;


import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductFilter;
import com.appyhome.appyproduct.mvvm.databinding.FragmentProductFilterBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.utils.helper.AppAnimator;
import com.appyhome.appyproduct.mvvm.utils.helper.ScreenUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.SelectableButtonGroup;

import javax.inject.Inject;

public class FilterFragment extends BaseFragment<FragmentProductFilterBinding, FilterViewModel> implements FilterNavigator {

    public static final String TAG = "FilterFragment";

    @Inject
    FilterViewModel mViewModel;

    FragmentProductFilterBinding mBinder;

    @Inject
    int mLayoutId;

    private SelectableButtonGroup mShippingForm;

    private SelectableButtonGroup mDiscount;

    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void updateUIFilter(ProductFilter filter) {
        mShippingForm.setCurrent(filter.shipping_from);
        mDiscount.setCurrent(filter.discount);
        mBinder.etPriceMax.setText(filter.price_max);
        mBinder.etPriceMin.setText(filter.price_min);
        mBinder.ratingBar.setRating(filter.rating);
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void applyFilter() {
        getViewModel().updateFilter(mShippingForm.getCurrent().getText().toString(), mDiscount.getCurrent().getText().toString(),
                mBinder.etPriceMin.getText().toString(), mBinder.etPriceMax.getText().toString(), mBinder.ratingBar.getRating());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    private void setUp() {
        mViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(this);
        int width = ScreenUtils.getScreenWidth(getContext());
        AppAnimator.slideFromRight(mBinder.llContent, width);
        mShippingForm = new SelectableButtonGroup(R.mipmap.bg_radius_button, R.mipmap.bg_radius_button_gray,
                getResources().getColor(R.color.white), getResources().getColor(R.color.dark_gray),
                mBinder.btnLocal, mBinder.btnOversea);
        mDiscount = new SelectableButtonGroup(R.mipmap.bg_radius_button, R.mipmap.bg_radius_button_gray,
                getResources().getColor(R.color.white), getResources().getColor(R.color.dark_gray),
                mBinder.btnBulkPurchase, mBinder.btnPromotional, mBinder.btnPremium);
        getViewModel().getCurrentFilter();
    }

    @Override
    public FilterViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
