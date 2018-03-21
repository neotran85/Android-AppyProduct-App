package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;


import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentProductSortBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.utils.helper.AppAnimator;

import javax.inject.Inject;

public class SortFragment extends BaseFragment<FragmentProductSortBinding, SortViewModel> {

    public static final String TAG = "FilterFragment";

    @Inject
    SortViewModel mViewModel;

    FragmentProductSortBinding mBinder;

    @Inject
    int mLayoutId;

    @Inject
    SortOptionsAdapter mAdapter;

    private SortOption mCurrentOption;

    private SortNavigator mNavigator;

    public static SortFragment newInstance(SortNavigator navigator) {
        Bundle args = new Bundle();
        SortFragment fragment = new SortFragment();
        fragment.setArguments(args);
        fragment.setNavigator(navigator);
        return fragment;
    }

    public void setNavigator(SortNavigator navigator) {
        mNavigator = navigator;
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
        mViewModel.setNavigator(mNavigator);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.setNavigator(mNavigator);
        mAdapter.setUp(this.getContext(), mViewModel.sortOptions, mNavigator);
        mBinder.lvSortOptions.setAdapter(mAdapter);
        mCurrentOption = mViewModel.getCurrentSortOption();
        int heightOfView = mViewModel.sortOptions.length * getResources().getDimensionPixelSize(R.dimen.height_sort_option_item);
        AppAnimator.dropdown(mBinder.lvSortOptions, heightOfView);
        AppAnimator.fadeIn(mBinder.llRestOfLayout);
    }

    public SortOption getCurrentSortOption() {
        return mCurrentOption;
    }

    public void setCurrentSortOption(SortOption opt) {
        mCurrentOption = opt;
    }

    @Override
    public SortViewModel getViewModel() {
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
