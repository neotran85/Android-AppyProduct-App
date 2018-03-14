package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.filter;


import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.FragmentProductFilterBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.utils.helper.AppAnimator;
import com.appyhome.appyproduct.mvvm.utils.helper.ScreenUtils;

import javax.inject.Inject;

public class FilterFragment extends BaseFragment<FragmentProductFilterBinding, FilterViewModel> implements FilterNavigator {

    public static final String TAG = "FilterFragment";

    @Inject
    FilterViewModel mViewModel;

    FragmentProductFilterBinding mBinder;

    @Inject
    int mLayoutId;

    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
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
