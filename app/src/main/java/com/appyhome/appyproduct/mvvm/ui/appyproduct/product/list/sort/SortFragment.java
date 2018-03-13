package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.sort;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.FragmentSampleBinding;
import com.appyhome.appyproduct.mvvm.databinding.FragmentSortBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

public class SortFragment extends BaseFragment<FragmentSortBinding, SortViewModel> implements SortNavigator {

    public static final String TAG = "SortFragment";

    @Inject
    SortViewModel mViewModel;

    FragmentSortBinding mBinder;

    @Inject
    int mLayoutId;

    @Inject
    SortOptionsAdapter mAdapter;

    private SortOption mCurrentOption;

    public static SortFragment newInstance() {
        Bundle args = new Bundle();
        SortFragment fragment = new SortFragment();
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
        mAdapter.setUp(this.getContext(), mViewModel.sortOptions, this);
        mBinder.lvSortOptions.setAdapter(mAdapter);
        mCurrentOption = mViewModel.getCurrentSortOption();
    }

    @Override
    public void onItemClick(View view) {
        mCurrentOption.checked.set(false);
        SortOption option = (SortOption) view.getTag();
        option.checked.set(true);
        mCurrentOption = option;
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
