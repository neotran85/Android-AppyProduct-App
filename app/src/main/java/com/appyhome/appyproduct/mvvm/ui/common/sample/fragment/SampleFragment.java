package com.appyhome.appyproduct.mvvm.ui.common.sample.fragment;


import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.FragmentSampleBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

public class SampleFragment extends BaseFragment<FragmentSampleBinding, SampleViewModel> implements SampleNavigator {

    public static final String TAG = "FavoriteFragment";

    @Inject
    SampleViewModel mViewModel;

    FragmentSampleBinding mBinder;

    @Inject
    int mLayoutId;

    public static SampleFragment newInstance() {
        Bundle args = new Bundle();
        SampleFragment fragment = new SampleFragment();
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
    }

    @Override
    public SampleViewModel getViewModel() {
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
